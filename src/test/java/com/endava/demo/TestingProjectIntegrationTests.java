package com.endava.demo;

import com.endava.demo.config.TestContext;
import com.endava.demo.controller.UserController;
import com.endava.demo.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TestingProjectIntegrationTests {

    private ResponseEntity entity;
    private Long someNumber = 100L;

    private UserDto userDto = UserDto.builder()
            .id(someNumber)
            .loginName("testUser")
            .password("userPassword")
            .email("134qewradsfzxcv1324adsfzxcv@qwer.bg")
            .firstName("the")
            .lastName("first")
            .role("template")
            .build();

    @Autowired
    private UserController userController;

    @Before
    public void setUp() {
        ResponseEntity testEntity = this.userController.findUser(this.userDto.getId());
        if (testEntity.getStatusCode() == HttpStatus.OK) {
            this.userController.deleteUser(this.userDto.getId());
        }
    }

    @After
    public void clear() {
        ResponseEntity testEntity = this.userController.findUser(this.userDto.getId());
        if (testEntity.getStatusCode() == HttpStatus.OK) {
            this.userController.deleteUser(this.userDto.getId());
        }
    }

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
        this.userDto = UserDto.builder()
                .id(someNumber)
                .loginName("testUser")
                .password("userPassword")
                .email("134qewradsfzxcv1324adsfzxcv@qwer.bg")
                .firstName("the")
                .lastName("second")
                .role("template")
                .build();
        this.entity = this.userController.createOrUpdateUser(this.userDto);
        assertTrue(this.entity.getBody().toString().contains("second"));
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
