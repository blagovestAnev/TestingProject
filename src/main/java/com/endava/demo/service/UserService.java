package com.endava.demo.service;

import com.endava.demo.dto.UserDto;
import java.util.Optional;

public interface UserService {

    UserDto createOrUpdate(UserDto newUser);
    Optional<UserDto> find(String loginName);
    void delete(String loginName);
}
