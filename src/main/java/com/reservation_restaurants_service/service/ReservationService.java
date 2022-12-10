package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.exception.ReservationNotFoundException;
import com.reservation_restaurants_service.repository.ReservationRepository;
import com.reservation_restaurants_service.service.mapper.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public ReservationDto save(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.convertReservationDtoToReservation(reservationDto);
        reservationRepository.save(reservation);
        return reservationMapper.convertReservationToReservationDto(reservation);
    }

    public ReservationDto findReservationById(Long id) {
        Optional<Reservation> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            return reservationMapper.convertReservationToReservationDto(reservationById.get());
        } else {
            throw new ReservationNotFoundException();
        }
    }

    public List<ReservationDto> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::convertReservationToReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationDto update(ReservationDto incomeReservationDto) {
        ReservationDto savedReservationDto = findReservationById(incomeReservationDto.getId());
        savedReservationDto = reservationMapper.convertReservationDtoToReservationDto(incomeReservationDto, savedReservationDto);
        save(savedReservationDto);
        return savedReservationDto;
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