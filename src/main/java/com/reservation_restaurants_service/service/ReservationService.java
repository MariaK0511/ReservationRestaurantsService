package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.Status;
import com.reservation_restaurants_service.exception.ReservationNotFoundException;
import com.reservation_restaurants_service.repository.ReservationRepository;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.ReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final RestaurantRepository restaurantRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ReservationMapper reservationMapper, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.reservationMapper = reservationMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public ReservationDto save(ReservationDto reservationDto, long restaurantId, long userId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        Optional<User> user = userRepository.findById(userId);

        Reservation reservation = reservationMapper.convertReservationDtoToReservation(reservationDto,
                restaurant.get(), user.get());
        reservation.setCreationTime(LocalDateTime.now());
        reservationRepository.save(reservation);
        logger.info("reservation for {} was created", user.get().getNickname());
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

    public ReservationDto update(ReservationDto reservationDto, long id) {
        Optional<Reservation> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            Reservation editedReservation = reservationById.get();
            editedReservation.setGuests(reservationDto.getGuests());
            reservationRepository.save(editedReservation);
            logger.info("reservation  was updated");
            return reservationMapper.convertReservationToReservationDto(editedReservation);
        }
        throw new ReservationNotFoundException();
    }

    public void delete(Long id) {
        Optional<Reservation> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            reservationRepository.delete(reservationById.get());
            logger.info("reservation  was deleted");
        } else {
            throw new ReservationNotFoundException();
        }
    }

    public List<ReservationDto> findByUserId(long id) {
        List<Reservation> reservations = reservationRepository.findByUserId(id);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservations.forEach(reservation -> reservationDtoList
                .add(reservationMapper.convertReservationToReservationDto(reservation)));
        return reservationDtoList;
    }

    public List<ReservationDto> findAllReservationsByUserId(long userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservationMapper::convertReservationToReservationDto)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> findAllReservationsByRestaurantId(long restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(reservationMapper::convertReservationToReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationDto setStatusToReservation(long id, Status status) {
        Optional<Reservation> reservationById = reservationRepository.findById(id);
        if (reservationById.isPresent()) {
            Reservation reservation = reservationById.get();
            reservation.setStatus(status);
            reservation.setTimeOfStatusChange(LocalDateTime.now());
            reservationRepository.save(reservation);
            logger.info("status {} for reservation was added", reservation.getStatus());
            return reservationMapper.convertReservationToReservationDto(reservation);
        }
        throw new ReservationNotFoundException();
    }
}