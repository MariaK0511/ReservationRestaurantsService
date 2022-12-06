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
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<Reservation> save(@RequestBody Reservation reservation) {
        //перевести запрос и ответ на дто тип резервации
        Reservation saveReservation = reservationService.save(reservation);
        return ok(saveReservation);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> findById(@PathVariable("reservationId") Long reservationId) {
        Reservation reservationById = reservationService.findReservationById(reservationId);
        return new ResponseEntity<>(reservationById, OK);
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> findAll() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return ok(reservations);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> update(@PathVariable("reservationId") Long reservationId,
                                              @RequestBody ReservationDto reservationDto) {
        reservationDto.setId(reservationId);
        Reservation reservation = reservationService.update(reservationDto);
        return new ResponseEntity<>(reservation, OK);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<User> deleteUser(@PathVariable("reservationId") Long reservationId) {
        reservationService.delete(reservationId);
        return new ResponseEntity<>(OK);
    }
}