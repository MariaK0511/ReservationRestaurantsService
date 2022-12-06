package com.reservation_restaurants_service.controller;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private  final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping()
    public ResponseEntity<Restaurant> save(@RequestBody Restaurant restaurant) {
        //где дто
        Restaurant saveRestaurant = restaurantService.save(restaurant);
        return ok(saveRestaurant);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> findById(@PathVariable("restaurantId") Long restaurantId) {
        Restaurant restaurantById = restaurantService.findRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurantById, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> findAll() {
       List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        return ok(restaurants);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> update(@PathVariable("restaurantId") Long restaurantId,
                                             @RequestBody RestaurantDto restaurantDto) {
        restaurantDto.setId(restaurantId);
        Restaurant restaurant = restaurantService.update(restaurantDto);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantService.delete(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
