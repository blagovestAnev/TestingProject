package com.endava.demo.mapper;

import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

}
