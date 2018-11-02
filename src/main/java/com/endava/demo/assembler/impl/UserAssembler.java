package com.endava.demo.assembler.impl;

import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler implements com.endava.demo.assembler.UserAssembler {

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .loginName(user.getLoginName())
                .password(user.getPassword())
                .salt(user.getSalt())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLoginName(userDto.getLoginName());
        user.setPassword(userDto.getPassword());
        user.setSalt(userDto.getSalt());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        return user;
    }
}
