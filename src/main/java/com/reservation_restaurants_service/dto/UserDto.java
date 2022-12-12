package com.reservation_restaurants_service.dto;

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
    private long id;
    @NotBlank(message = "Field cannot be empty")
    private String username;
    @NotBlank(message = "Field cannot be empty")
    private String surname;
    @NotBlank(message = "Field cannot be empty")
    private String nickname;
    @NotBlank(message = "Field cannot be empty")
    private String email;
    @NotBlank(message = "Field cannot be empty")
    private String password;
    private long phoneNumber;
}