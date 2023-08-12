package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.repository.ReservationRepository;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ReservationInitializeService {

    private final ReservationRepository reservationRepository;

    public ReservationInitializeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostConstruct
    public void initReservation() {
        Reservation reservation = new Reservation();
        reservation.setRestaurant(reservation.getRestaurant());
        reservation.setCreationTime(reservation.getCreationTime());
        reservation.setGuests(2);
        reservation.setStatus(reservation.getStatus());
        reservation.setTimeOfStatusChange(reservation.getTimeOfStatusChange());
        reservationRepository.save(reservation);
    }
}