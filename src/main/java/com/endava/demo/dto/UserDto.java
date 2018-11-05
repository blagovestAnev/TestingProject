package com.endava.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserDto {

    @NotNull
    @ApiModelProperty(notes = "User provided number.",required = true)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    @ApiModelProperty(notes = "User provided login name.", required = true)
    private String loginName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    @ApiModelProperty(notes = "User provided password.", required = true)
    private String password;

    @ApiModelProperty(notes = "System generated additional element to enforce password.")
    private int salt;

    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    @ApiModelProperty(notes = "User provided first name.")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    @ApiModelProperty(notes = "User provided last name.")
    private String lastName;

    @ApiModelProperty(notes = "User provided role.")
    private String role;

}
