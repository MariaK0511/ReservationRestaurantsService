package com.reservation_restaurants_service.dto;

import com.reservation_restaurants_service.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Parameter(description = "User id")
    private long id;
    @NotBlank(message = "Field cannot be empty")
    @Parameter(description = "User name")
    private String username;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "User surname")
    private String surname;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "User nickname")
    private String nickname;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "User email")
    private String email;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "User password")
    private String password;
    @ApiModelProperty(notes = "User phone number")
    private String phoneNumber;
    @ApiModelProperty(notes = "User role")
    private UserRole userRole;
}