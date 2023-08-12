package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInitializeService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantInitializeService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void initRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Patrick Pub");
        restaurant.setAddress("str.Hiercena, 4");
        restaurant.setPhoneNumber("+375296199865");
        restaurant.setRating(0);
        restaurant.setLat(0.0);
        restaurant.setLon(0.0);
        restaurantRepository.save(restaurant);
    }
}