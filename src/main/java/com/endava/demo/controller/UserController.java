package com.endava.demo.controller;

import com.endava.demo.dto.UserDto;
import com.endava.demo.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(description = "Contains operations with the user-data.")
public class UserController {

    private final UserService userService;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 10;

    /**
     * Endpoint for user create or update
     * It will create or update user, depending on is it found in the database or not. The return object will contain
     * all the provided from the request info, also with encrypted password and salt number, together with response
     * code.
     *
     * @param userDto summarized provided json information for the object to work with
     * @return ResponseEntity with the body of the returned successful create or update method
     */
    @ApiOperation(value = "Updates \"user\" information based on \"login name\" and \"password\". If such don't exist, the app will create a new one.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns \"OK\" response when successful update/create operation has been performed."),
                           @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
                           @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
                           @ApiResponse(code = 404, message = "The resource you were trying to reach is not found.")})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createOrUpdateUser(@ApiParam(name = "userDto", value = "Represents the object values, based on which to create/store user.") @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(this.userService.createOrUpdate(userDto));
    }

    /**
     * Finds object, based on login name of the user
     * Checks for specific user, based on user name (this is the primary key in the user table in the database). There
     * is 10% chance for purposely integrated fault, which will invoke the fallback method (this simulates threads latency
     * in the server for some reason).
     *
     * @param loginName String, which is the database primary key.
     * @return ResponseEntity with status code and empty or full object info, depending on whether the object has been
     * found in database or not
     * @throws InterruptedException will be thrown, because the thread sleep can be interrupted(which exactly is the job
     * of Hystrix in this case to handle)
     */
    @HystrixCommand(fallbackMethod = "fallbackFindUser",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    @ApiOperation(value = "Return information for \"user\", based on \"login name\".")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns user information, based on \"id\" with OK response."),
                           @ApiResponse(code = 204, message = "Returns \"No content\" message, when user with this \"id\" is not found."),
                           @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
                           @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                           @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
                           @ApiResponse(code = 500, message = "Returns information that \"user\" with this \"login name\" don't exist in database.")})
    @GetMapping(value = "/find/{loginName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity findUser(@ApiParam(name = "loginName", value = "This is the searched object login name.") @PathVariable String loginName) throws InterruptedException {

        // At random moments (10% chance) this will invoke the fallback method below (this simulates delayed
        // thread/process, which to be handled by Hystrix).
        if(getRandomNumber() == 5) {
            Thread.sleep(3000);
        }
        return this.userService.find(loginName).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @ApiOperation(value = "Deletes \"user\", based on \"login name\".")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns \"OK\" response when the \"user\" has been found and deleted."),
                           @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
                           @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
                           @ApiResponse(code = 500, message = "Returns information that \"user\" with this \"login name\" don't exist in database.")})
    @DeleteMapping(value = "/delete/{loginName}")
    public ResponseEntity deleteUser(@ApiParam(name = "loginName", value = "This is the searched object login name.") @PathVariable String loginName) {
        this.userService.delete(loginName);
        return ResponseEntity.ok().build();
    }

    /**
     * Returns OK response with empty body, which to be as a mark that it works - usually the body with OK status
     * is with data from the database.
     * @param loginName it's not used in the implementation of this method, but by default the fallback
     *                  parameters have to be the same with the ones of the method, which needs to be supported
     *                  by Hystrix
     * @return response ok status with empty body
     */
    @SuppressWarnings("unused")
    private ResponseEntity fallbackFindUser(String loginName) {
        return ResponseEntity.ok().build();
    }

    /**
     * Creates a random number between minimum and maximum.
     * @return random integer number
     */
    private int getRandomNumber() {
        return (int) (Math.random() * ((MAX_NUMBER - MIN_NUMBER) + 1)) + MIN_NUMBER;
    }
}
