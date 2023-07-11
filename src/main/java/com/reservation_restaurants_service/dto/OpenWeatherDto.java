package com.reservation_restaurants_service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenWeatherDto {

    @ApiModelProperty(notes = "currently temperature")
    private Main main;
    @ApiModelProperty(notes = "currently type of weather")
    private List<Weather> weather;
}