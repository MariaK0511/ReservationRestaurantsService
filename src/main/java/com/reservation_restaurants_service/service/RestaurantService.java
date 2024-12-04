package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.dto.WeatherDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.exception.ResourceNotFoundException;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.service.mapper.RestaurantMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    private final CorrectionPhoneNumber correctionPhoneNumber;
    private final WeatherService weatherService;


    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, CorrectionPhoneNumber correctionPhoneNumber, WeatherService weatherService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.correctionPhoneNumber = correctionPhoneNumber;
        this.weatherService = weatherService;
    }

    public RestaurantDto save(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.convertRestaurantDtoToRestaurant(restaurantDto);
        restaurant.setPhoneNumber(correctionPhoneNumber.correctPhoneNumber(restaurant.getPhoneNumber()));
        restaurantRepository.save(restaurant);
        logger.info("restaurant {} was created", restaurant.getName());
        return restaurantMapper.convertRestaurantToRestaurantDto(restaurant);
    }

    public RestaurantDto findRestaurantById(Long id, boolean wantWeather) {
        Optional<Restaurant> restaurantById = restaurantRepository.findById(id);
        if (restaurantById.isPresent()) {
            RestaurantDto restaurantDto = restaurantMapper.convertRestaurantToRestaurantDto(restaurantById.get());
            if (wantWeather && restaurantDto.getLon() != 0.0 && restaurantDto.getLat() != 0.0) {
                WeatherDto weather = weatherService.getWeather(restaurantDto.getLat(), restaurantDto.getLon());
                restaurantDto.setWeatherDto(weather);
            }
            return restaurantDto;
        } else {
            throw ResourceNotFoundException.of(Restaurant.class, id);
        }
    }

    public List<RestaurantDto> findAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::convertRestaurantToRestaurantDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto update(RestaurantDto incomeRestaurantDto) {
        RestaurantDto savedRestaurantDto = findRestaurantById(incomeRestaurantDto.getId(), false);
        savedRestaurantDto = restaurantMapper.convertRestaurantDtoToRestaurantDto(incomeRestaurantDto, savedRestaurantDto);
        savedRestaurantDto.setPhoneNumber(correctionPhoneNumber.correctPhoneNumber(savedRestaurantDto.getPhoneNumber()));
        save(savedRestaurantDto);
        logger.info("restaurant {} was updated successfully", savedRestaurantDto.getName());
        return savedRestaurantDto;
    }

    public void delete(Long id) {
        Optional<Restaurant> restaurantById = restaurantRepository.findById(id);
        if (restaurantById.isPresent()) {
            restaurantRepository.delete(restaurantById.get());
            logger.info("restaurant {} was deleted", restaurantById.get().getName());
        } else {
            throw ResourceNotFoundException.of(Restaurant.class, id);
        }
    }
}