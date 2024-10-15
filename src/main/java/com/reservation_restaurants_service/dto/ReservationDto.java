package com.reservation_restaurants_service.dto;

import com.reservation_restaurants_service.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    @ApiModelProperty(notes = "Reservation id")
    private Long id;
    @ApiModelProperty(notes = "Time of reservation")
    private LocalDateTime creationTime;
    @ApiModelProperty(notes = "Reservation status")
    private Status status;
    @ApiModelProperty(notes = "Time of status change")
    private LocalDateTime timeStatusChange;
    private long guests;
}