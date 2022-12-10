package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.exception.ReservationNotFoundException;
import com.reservation_restaurants_service.exception.RestaurantNotFoundException;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public RestaurantDto save(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.convertRestaurantDtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);
        return restaurantMapper.convertRestaurantToRestaurantDto(restaurant);
    }

    public RestaurantDto findRestaurantById(Long id) {
        Optional<Restaurant> restaurantById = restaurantRepository.findById(id);
        if (restaurantById.isPresent()) {
            return restaurantMapper.convertRestaurantToRestaurantDto(restaurantById.get());
        } else throw new RestaurantNotFoundException();
    }

    public List<RestaurantDto> findAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::convertRestaurantToRestaurantDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto update(RestaurantDto incomeRestaurantDto) {
        RestaurantDto savedRestaurantDto = findRestaurantById(incomeRestaurantDto.getId());
        savedRestaurantDto = restaurantMapper.convertRestaurantDtoToRestaurantDto(incomeRestaurantDto, savedRestaurantDto);
        save(savedRestaurantDto);
        return savedRestaurantDto;
    }

    public void delete(Long id) {
        Optional<Restaurant> restaurantById = restaurantRepository.findById(id);
        if (restaurantById.isPresent()) {
            restaurantRepository.delete(restaurantById.get());
        } else {
            throw new ReservationNotFoundException();
        }
    }
}