package com.reservation_restaurants_service.controller;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("restaurant/{restaurantId}/reservation")
    public ResponseEntity<ReservationDto> save(@RequestBody ReservationDto reservationDto,
                                               @PathVariable("restaurantId")long restaurantId) {
        ReservationDto saveReservation = reservationService.save(reservationDto, restaurantId);
        return ok(saveReservation);
    }

    @GetMapping("restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> findById(@PathVariable("reservationId") Long reservationId) {
        ReservationDto reservationById = reservationService.findReservationById(reservationId);
        return new ResponseEntity<>(reservationById, OK);
    }

    @GetMapping("restaurant/reservations")
    public ResponseEntity<List<ReservationDto>> findAll() {
        List<ReservationDto> reservations = reservationService.findAllReservations();
        return ok(reservations);
    }

    @PutMapping("restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> update(@PathVariable("reservationId") Long reservationId,
                                              @RequestBody ReservationDto reservationDto) {
        reservationDto.setId(reservationId);
        ReservationDto reservation = reservationService.update(reservationDto,reservationId);
        return new ResponseEntity<>(reservation, OK);
    }

    @DeleteMapping("restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.delete(reservationId);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> addReservationToUser(@PathVariable("reservationId") Long reservationId,
                                                               @RequestBody ReservationDto reservationDto){
        reservationService.addReservationToUser(reservationDto,reservationId);
        return new ResponseEntity<>(OK);
    }
}