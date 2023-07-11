package com.reservation_restaurants_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {

    @ApiModelProperty(notes = "currently temperature")
    private double temperature;
    @ApiModelProperty(notes = "currently weather")
    private String typeOfWeather;
}