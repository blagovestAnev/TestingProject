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
    private String loginName = "testUser";
    private String password = "pass1234";

    private UserDto userDto = UserDto.builder()
            .loginName(this.loginName)
            .password(this.password)
            .email("134qdsfzxcv1324adsfzxcv@qwer.bg")
            .firstName("the")
            .lastName("first")
            .role("template")
            .build();

    @Autowired
    private UserController userController;

    @Before
    public void setUp() throws InterruptedException {
        ResponseEntity testEntity = this.userController.findUser(this.userDto.getLoginName());
        if (testEntity.getStatusCode() == HttpStatus.OK) {
            this.userController.deleteUser(this.userDto.getLoginName());
        }
    }

    @After
    public void clear() throws InterruptedException {
        ResponseEntity testEntity = this.userController.findUser(this.userDto.getLoginName());
        if (testEntity.getStatusCode() == HttpStatus.OK) {
            this.userController.deleteUser(this.userDto.getLoginName());
        }
    }

    @Test
    public void createUser() {
        this.entity = this.userController.createOrUpdateUser(this.userDto);
        assertEquals(this.entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateUser() throws InterruptedException {
        this.userController.createOrUpdateUser(this.userDto);
        this.entity = this.userController.findUser(this.userDto.getLoginName());
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        this.userDto = UserDto.builder()
                .loginName(this.loginName)
                .password(this.password)
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
    public void findUser() throws InterruptedException {
        this.entity = this.userController.createOrUpdateUser(this.userDto);
        this.entity = this.userController.findUser(this.userDto.getLoginName());
        assertEquals(this.entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteUser() {
        this.entity = this.userController.createOrUpdateUser(this.userDto);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        this.entity = this.userController.deleteUser(this.userDto.getLoginName());
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void throwExceptionIfNoSuchUserIdExist() {
        try {
            this.userController.deleteUser(this.userDto.getLoginName());
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }
}
