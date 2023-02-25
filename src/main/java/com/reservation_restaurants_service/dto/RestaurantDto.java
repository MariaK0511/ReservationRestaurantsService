package com.reservation_restaurants_service.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    @ApiModelProperty(notes = "Restaurant id")
    private long id;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "Restaurant name")
    private String name;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "Restaurant address")
    private String address;
    @NotBlank(message = "Field cannot be empty")
    @ApiModelProperty(notes = "Restaurant phone number")
    private String phoneNumber;
    @ApiModelProperty(notes = "Restaurant rating")
    private long rating;
}