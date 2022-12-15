package com.reservation_restaurants_service.dto;

import com.reservation_restaurants_service.enums.Status;
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
    private long id;
    private LocalDateTime creationTime;
    private Status status;
    private LocalDateTime timeStatusChange;
    private long guests;
}