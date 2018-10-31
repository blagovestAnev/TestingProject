package com.endava.demo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserDto {

    @NotNull
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    private String loginName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    private String lastName;
    private String role;

}
