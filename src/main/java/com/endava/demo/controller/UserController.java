package com.endava.demo.controller;

import com.endava.demo.dto.UserDto;
import com.endava.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "Contains operations for operations with the user-data.")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Updates \"user\" information based on \"id\",\"login name\" and \"password\". If such don't exist, the app will create a new one.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns \"OK\" response when successful update/create operation has been performed."),
                           @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
                           @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
                           @ApiResponse(code = 404, message = "The resource you were trying to reach is not found.")})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createOrUpdateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(this.userService.createOrUpdate(userDto));
    }

    @ApiOperation(value = "Return information for \"user\", based on \"id\".")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns user information, based on \"id\" with OK response."),
                           @ApiResponse(code = 204, message = "Returns \"No content\" message, when user with this \"id\" is not found."),
                           @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
                           @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                           @ApiResponse(code = 500, message = "Returns information that \"user\" with this id don't exist in database.")})
    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity findUser(@PathVariable Long id) {
        return this.userService.find(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @ApiOperation(value = "Deletes \"user\", based on \"id\".")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns \"OK\" response when the \"user\" has been found and deleted."),
                           @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
                           @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
                           @ApiResponse(code = 500, message = "Returns information that \"user\" with this id don't exist in database.")})
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
