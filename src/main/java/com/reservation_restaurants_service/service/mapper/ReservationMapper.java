package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    private final RestaurantMapper restaurantMapper;

    public ReservationMapper(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public Reservation convertReservationDtoToReservation(ReservationDto reservationDto, Restaurant restaurant, User user) {
        return new Reservation(reservationDto.getId(),
              restaurant,
                reservationDto.getCreationTime(),
                reservationDto.getGuests(), user);
    }


    public ReservationDto convertReservationToReservationDto(Reservation reservation) { //not finished yet!
        return new ReservationDto(reservation.getId(),
null, null,
                reservation.getCreationTime(),
                reservation.getGuests());

    }

//    public ReservationDto convertReservationDtoToReservationDto(ReservationDto incomeReservationDto,
//                                                                ReservationDto savedReservationDto) {
//        savedReservationDto.setRestaurantDto(incomeReservationDto.getRestaurantDto());
//        savedReservationDto.setCreationTime(incomeReservationDto.getCreationTime());
//        savedReservationDto.setGuests(incomeReservationDto.getGuests());
//        return savedReservationDto;
//    }
}