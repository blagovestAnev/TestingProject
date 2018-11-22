package com.endava.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserDto {

    @ApiModelProperty(notes = "System generated unique number.")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    @ApiModelProperty(notes = "User provided login name.", required = true)
    private String loginName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ,.'-]+$")
    @ApiModelProperty(notes = "User provided password.", required = true)
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,6}$")
    @ApiModelProperty(notes = "User provided personal mail.", required = true)
    private String email;

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
