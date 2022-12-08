package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReservationDto;
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

    public ReservationDto save(ReservationDto reservationDto) {
        reservationRepository.save(reservationDto);
        return reservationDto;
    }

    public ReservationDto findReservationById(Long id) {
        Optional<ReservationDto> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            return reservationById.get();
        } else {
            throw new ReservationNotFoundException();
        }
    }

    public List<ReservationDto> findAllReservations() {
        return reservationRepository.findAll();
    }

    public ReservationDto update(ReservationDto reservationDto) {
        ReservationDto editedReservation = findReservationById(reservationDto.getId());
        editedReservation.setRestaurantName(reservationDto.getRestaurantName());
        editedReservation.setGuests(reservationDto.getGuests());
        editedReservation.setCreationTime(reservationDto.getCreationTime());
        save(editedReservation);
        return editedReservation;
    }

    public void delete(Long id) {
        Optional<ReservationDto> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            reservationRepository.delete(reservationById.get());
        } else {
            throw new ReservationNotFoundException();
        }
    }
}