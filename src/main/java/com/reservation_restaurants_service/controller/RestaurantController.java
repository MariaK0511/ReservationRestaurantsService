package com.reservation_restaurants_service.controller;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.service.ReservationService;
import com.reservation_restaurants_service.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    public RestaurantController(RestaurantService restaurantService, ReservationService reservationService) {
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
    }

    @PostMapping("/restaurant/save")
    public ResponseEntity<RestaurantDto> save(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto saveRestaurant = restaurantService.save(restaurantDto);
        return ok(saveRestaurant);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantDto> findById(@PathVariable("restaurantId") Long restaurantId) {
        RestaurantDto restaurantById = restaurantService.findRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurantById, HttpStatus.OK);
    }

    @GetMapping("/restaurant/restaurants")
    public ResponseEntity<List<RestaurantDto>> findAll() {
        List<RestaurantDto> restaurants = restaurantService.findAllRestaurants();
        return ok(restaurants);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantDto> update(@PathVariable("restaurantId") Long restaurantId,
                                                @RequestBody RestaurantDto restaurantDto) {
        restaurantDto.setId(restaurantId);
        RestaurantDto restaurant = restaurantService.update(restaurantDto);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantDto> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantService.delete(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/reservations")
    public ResponseEntity<List<ReservationDto>> showReservationsOfRestaurant(@PathVariable("restaurantId") Long restaurantId) {
      List<ReservationDto> reservationDtoList = reservationService.findAllReservationsByRestaurantId(restaurantId);
        return ok(reservationDtoList);
    }
}