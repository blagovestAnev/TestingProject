package com.endava.demo;

import com.endava.demo.config.TestContext;
import com.endava.demo.controller.UserController;
import com.endava.demo.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class TestingProjectIntegrationTests {

    private ResponseEntity entity;
    private UserDto userDto = UserDto.builder()
            .id(100L)
            .loginName("testUser")
            .password("userPassword")
            .firstName("just")
            .lastName("user")
            .role("using")
            .build();

    @Autowired
    private UserController userController;

    @Test
    public void createUser() {
        this.userController.createOrUpdateUser(this.userDto);
        this.entity = this.userController.findUser(this.userDto.getId());
        assertEquals(this.entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateUser() {
        this.userController.createOrUpdateUser(this.userDto);
        this.entity = this.userController.findUser(userDto.getId());
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        UserDto userDto2 = UserDto.builder()
                .id(100L)
                .loginName("testUser2")
                .password("userPassword")
                .firstName("just")
                .lastName("user")
                .role("using")
                .build();
        this.entity = this.userController.createOrUpdateUser(userDto2);
        assertTrue(entity.getBody().toString().contains("testUser2"));
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void findUser() {
        this.entity = this.userController.createOrUpdateUser(userDto);
        this.entity = this.userController.findUser(userDto.getId());
        assertEquals(this.entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteUser() {
        this.entity = this.userController.createOrUpdateUser(this.userDto);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        this.entity = this.userController.deleteUser(this.userDto.getId());
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void throwExceptionIfNoSuchUserIdExist() {
        try {
            this.userController.deleteUser(this.userDto.getId());
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }
}
