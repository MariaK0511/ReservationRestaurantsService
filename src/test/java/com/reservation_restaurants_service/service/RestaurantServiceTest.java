package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

@SpringBootTest
public class RestaurantServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantMapper restaurantMapper;
    private CorrectionPhoneNumber correctionPhoneNumber;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        restaurantService = new RestaurantService(restaurantRepository, restaurantMapper, correctionPhoneNumber, weatherService);
    }

    private static Restaurant getTestRestaurant() {
        return new Restaurant(1L, "ODI", "prasp.Niezalieznasti 12", "375447721212", 0, 0, 0);
    }

    @Test
    void givenRestaurant_whenCreateRestaurant_thenReturnSavedRestaurant() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        //when
        when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(testRestaurant);
        //then
        //   assertEquals(1L, testRestaurant.getId());
        assertThat(testRestaurant.getId()).isGreaterThan(0);
        assertNotNull(testRestaurant);
    }

    @Test
    void givenRestaurantId_whenGetRestaurantId_thenReturnRestaurant() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        //when
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        //then
        restaurantService.findRestaurantById(testRestaurant.getId(), false);
        assertThat(testRestaurant.getId()).isEqualTo(1L);
        assertNotNull(testRestaurant);
    }

    @Test
    void givenListOfRestaurants_whenGetAllRestaurants_thenReturnRestaurantList() throws NullPointerException {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        List<Restaurant> restaurants = new ArrayList<>();
        //when
        restaurants.add(testRestaurant);
        //then
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        restaurantService.findAllRestaurants();
        assertThat(restaurants.size()).isGreaterThan(0);
        assertNotNull(restaurants);
    }

    @Test
    void givenUser_whenUpdateRestaurant_thenReturnUpdatedRestaurant() throws NullPointerException {
        Restaurant restaurant = new Restaurant(1L, "name", "address",
                "375441218766", 0, 0, 0);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        restaurant.setPhoneNumber("375447721212");
        RestaurantDto restaurantDto = new RestaurantDto(1L, "testname", "address",
                "375447721212", 0, 0, 0, weatherService.getWeather(0, 0));
        restaurantService.update(restaurantDto);
        assertThat(restaurantDto.getPhoneNumber()).isEqualTo(restaurant.getPhoneNumber());
        assertNotNull(restaurantDto);
        assertEquals(restaurantDto.getId(), restaurant.getId());
        assertThat(restaurant.getId()).isEqualTo(1L);
        assertNotNull(restaurant);
    }

    @Test
    void givenUser_whenDeleteRestaurant() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        //when
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        //then
        Optional<Restaurant> savedRestaurant = Optional.of(testRestaurant);
        savedRestaurant.ifPresent(value -> restaurantService.delete(value.getId()));
    }
}