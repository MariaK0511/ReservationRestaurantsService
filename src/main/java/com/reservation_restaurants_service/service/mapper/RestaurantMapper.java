package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public Restaurant convertRestaurantDtoToRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
        restaurant.setRating(restaurant.getRating());
        restaurant.setLat(restaurant.getLat());
        restaurant.setLon(restaurant.getLon());
        return restaurant;
    }

    public RestaurantDto convertRestaurantToRestaurantDto(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setPhoneNumber(restaurant.getPhoneNumber());
        restaurantDto.setRating(restaurant.getRating());
        restaurantDto.setLat(restaurant.getLat());
        restaurantDto.setLon(restaurant.getLon());
        return restaurantDto;
    }

    public RestaurantDto convertRestaurantDtoToRestaurantDto(RestaurantDto incomeRestaurantDto,
                                                             RestaurantDto savedRestaurantDto) {
        savedRestaurantDto.setName(incomeRestaurantDto.getName());
        savedRestaurantDto.setAddress(incomeRestaurantDto.getAddress());
        savedRestaurantDto.setPhoneNumber(incomeRestaurantDto.getPhoneNumber());
        savedRestaurantDto.setRating(incomeRestaurantDto.getRating());
        savedRestaurantDto.setLat(incomeRestaurantDto.getLat());
        savedRestaurantDto.setLon(incomeRestaurantDto.getLon());
        return savedRestaurantDto;
    }
}