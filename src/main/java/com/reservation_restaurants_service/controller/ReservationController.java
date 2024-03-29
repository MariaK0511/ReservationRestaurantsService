package com.reservation_restaurants_service.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation(value = "Add reservation by restaurant id and user id", notes = "Creating and adding reservation in database")
    @PostMapping("/reservation/restaurant/{restaurantId}/user/{userId}/reservation")
    public ResponseEntity<ReservationDto> save(@RequestBody ReservationDto reservationDto,
                                               @PathVariable("restaurantId") long restaurantId,
                                               @PathVariable("userId") long userId) {
        ReservationDto saveReservation = reservationService.save(reservationDto, restaurantId, userId);
        return ok(saveReservation);
    }

    @ApiOperation(value = "Get reservation by  id", notes = "Return reservation as per the id")
    @GetMapping("/reservation/restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> findById(@PathVariable("reservationId") Long reservationId) {
        ReservationDto reservationById = reservationService.findReservationById(reservationId);
        return new ResponseEntity<>(reservationById, OK);
    }

    @ApiOperation(value = "Get all reservations", notes = "Return all reservations")
    @GetMapping("/reservation/restaurant/reservations")
    public ResponseEntity<List<ReservationDto>> findAll() {
        List<ReservationDto> reservations = reservationService.findAllReservations();
        return ok(reservations);
    }

    @ApiOperation(value = "Update reservation by id", notes = "Return updated reservation as per the id")
    @PutMapping("/reservation/restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> update(@PathVariable("reservationId") Long reservationId,
                                                 @RequestBody ReservationDto reservationDto) {
        reservationDto.setId(reservationId);
        ReservationDto reservation = reservationService.update(reservationDto, reservationId);
        return new ResponseEntity<>(reservation, OK);
    }

    @ApiOperation(value = "Delete reservation by id")
    @DeleteMapping("/reservation/restaurant/{reservationId}")
    public ResponseEntity<ReservationDto> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.delete(reservationId);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Set status to reservation by restaurant id and reservation id", notes = "Return reservation as per the id")
    @PutMapping("reservation/status/restaurant/{restaurantId}/reservation/{reservationId}/reservationStatus")
    public ResponseEntity<ReservationDto> setReservationStatus(@PathVariable("reservationId") Long reservationId,
                                                               @PathVariable("restaurantId") Long restaurantId,
                                                               @RequestBody ReservationDto reservationDto) {
        ReservationDto changeReservationDto = reservationService.setStatusToReservation(reservationId, reservationDto.getStatus());
        return new ResponseEntity<>(changeReservationDto, OK);
    }
}