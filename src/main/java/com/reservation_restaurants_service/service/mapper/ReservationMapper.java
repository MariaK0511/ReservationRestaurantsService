package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    private final RestaurantMapper restaurantMapper;

    public ReservationMapper(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public Reservation convertReservationDtoToReservation(ReservationDto reservationDto) {
        return new Reservation(reservationDto.getId(),
               restaurantMapper.convertRestaurantDtoToRestaurant(reservationDto.getRestaurantDto()),
                reservationDto.getCreationTime(),
                reservationDto.getGuests(), null);
    }

    public ReservationDto convertReservationToReservationDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(),
                restaurantMapper.convertRestaurantToRestaurantDto(reservation.getRestaurant()),
                reservation.getCreationTime(),
                reservation.getGuests());

    }

    public ReservationDto convertReservationDtoToReservationDto(ReservationDto incomeReservationDto,
                                                                ReservationDto savedReservationDto) {
        savedReservationDto.setRestaurantDto(incomeReservationDto.getRestaurantDto());
        savedReservationDto.setCreationTime(incomeReservationDto.getCreationTime());
        savedReservationDto.setGuests(incomeReservationDto.getGuests());
        return savedReservationDto;
    }
}