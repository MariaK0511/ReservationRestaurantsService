package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.User;

public class ReservationMapper {
    public Reservation convertReservationDto(ReservationDto reservationDto, User user) {
        return new Reservation(reservationDto.getId(),
                reservationDto.getRestaurantName(),
                reservationDto.getCreationTime(),
                reservationDto.getGuests(), user);
    }
}