package com.endava.demo.controller;

import com.endava.demo.dto.UserDto;
import com.endava.demo.service.UserService;
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
public class UserController {

    private final UserService userService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createOrUpdateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(this.userService.createOrUpdate(userDto));
    }

    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity findUser(@PathVariable Long id) {
        return this.userService.find(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
