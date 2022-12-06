package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.exception.ReservationNotFoundException;
import com.reservation_restaurants_service.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation save(Reservation reservation) {
        reservationRepository.save(reservation);
        return reservation;
    }

    public Reservation findReservationById(Long id) {
        Optional<Reservation> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            return reservationById.get();
        } else {
            throw new ReservationNotFoundException();
        }
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation update(ReservationDto reservationDto) {
        Reservation editedReservation = findReservationById(reservationDto.getId());
        editedReservation.setRestaurantName(reservationDto.getRestaurantName());
        editedReservation.setGuests(reservationDto.getGuests());
        editedReservation.setCreationTime(reservationDto.getCreationTime());
        //сохранение?
        return editedReservation;
    }

    public void delete(Long id) {
        Optional<Reservation> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            reservationRepository.delete(reservationById.get());
        } else {
            throw new ReservationNotFoundException();
        }
    }
}