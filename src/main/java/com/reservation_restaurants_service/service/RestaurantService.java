package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.exception.ReservationNotFoundException;
import com.reservation_restaurants_service.exception.RestaurantNotFoundException;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantDto save(RestaurantDto restaurantDto) {
        restaurantRepository.save(restaurantDto);
        return restaurantDto;
    }

    public RestaurantDto findRestaurantById(Long id) {
        Optional<RestaurantDto> restaurantById = restaurantRepository.findById(id);
        if (restaurantById.isPresent()) {
            return restaurantById.get();
        } else throw new RestaurantNotFoundException();
    }

    public List<RestaurantDto> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public RestaurantDto update(RestaurantDto restaurantDto) {
        RestaurantDto editedRestaurant = findRestaurantById(restaurantDto.getId());
        editedRestaurant.setName(restaurantDto.getName());
        editedRestaurant.setAddress(restaurantDto.getAddress());
        editedRestaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
        editedRestaurant.setRating(restaurantDto.getRating());
        save(editedRestaurant);
        // добавить маперы, чтобы не писать гетеры и сетторы
        return editedRestaurant;
    }

    public void delete(Long id) {
        Optional<RestaurantDto> restaurantById = restaurantRepository.findById(id);
        if (restaurantById.isPresent()) {
            restaurantRepository.delete(restaurantById.get());
        } else {
            throw new ReservationNotFoundException();
        }
    }
}