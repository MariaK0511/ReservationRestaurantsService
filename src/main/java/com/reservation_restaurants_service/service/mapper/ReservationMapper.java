package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class ReservationMapper {

    public Reservation convertReservationDtoToReservation(ReservationDto reservationDto, Restaurant restaurant, User user) {
        return new Reservation(reservationDto.getId(),
                restaurant, now(),
                reservationDto.getGuests(), user);
    }

    public ReservationDto convertReservationToReservationDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(),
                now(), reservation.getGuests());
    }
}