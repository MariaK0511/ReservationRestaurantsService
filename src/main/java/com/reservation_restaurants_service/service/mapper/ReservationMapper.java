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
        reservation.setId(reservationDto.getId());
        reservation.setGuests(reservationDto.getGuests());
        reservation.setCreationTime(reservationDto.getCreationTime());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setTimeOfStatusChange(reservationDto.getTimeStatusChange());
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        return reservation;
    }

    public ReservationDto convertReservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGuests(reservation.getGuests());
        reservationDto.setCreationTime(reservation.getCreationTime());
        reservationDto.setStatus(reservation.getStatus());
        reservationDto.setTimeStatusChange(reservation.getTimeOfStatusChange());
        return reservationDto;
    }
}