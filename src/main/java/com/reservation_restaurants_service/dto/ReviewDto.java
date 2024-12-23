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
public class ReviewDto {
    @ApiModelProperty(notes = "Review id")
    @Parameter(description = "Review id")
    private Long id;
    @ApiModelProperty(notes = "User review for restaurant")
    @Parameter(description = "User review for restaurant")
    private String review;

}
