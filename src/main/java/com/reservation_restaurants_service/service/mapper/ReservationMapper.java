package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation convertReservationDtoToReservation(ReservationDto reservationDto,
                                                          Restaurant restaurant,
                                                          User user) {
        Reservation reservation = new Reservation();
        reservation.setGuests(reservationDto.getGuests());
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        return reservation;
    }

    public ReservationDto convertReservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setCreationTime(reservation.getCreationTime());
        reservationDto.setGuests(reservation.getGuests());
        reservationDto.setStatus(reservation.getStatus());
        reservationDto.setTimeStatusChange(reservation.getTimeOfStatusChange());
        return reservationDto;
    }
}