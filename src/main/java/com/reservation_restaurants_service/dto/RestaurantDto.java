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
public class RestaurantDto {
    private long id;
    @NotBlank(message = "Field cannot be empty")
    private String name;
    @NotBlank(message = "Field cannot be empty")
    private String address;
    @NotBlank(message = "Field cannot be empty")
    private long phoneNumber;
    private double rating;
}