package com.endava.demo.mapper;

import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}
