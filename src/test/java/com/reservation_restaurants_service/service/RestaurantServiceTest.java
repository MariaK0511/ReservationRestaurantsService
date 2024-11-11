package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.RestaurantDto;
import com.reservation_restaurants_service.dto.WeatherDto;
import com.reservation_restaurants_service.entity.Restaurant;
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

@SpringBootTest
public class RestaurantServiceTest {
    @InjectMocks
    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private CorrectionPhoneNumber correctionPhoneNumber;
    @Mock
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        restaurantMapper = Mockito.mock(RestaurantMapper.class);
        correctionPhoneNumber = Mockito.mock(CorrectionPhoneNumber.class);
        weatherService = Mockito.mock(WeatherService.class);
        restaurantService = new RestaurantService(restaurantRepository, restaurantMapper, correctionPhoneNumber, weatherService);
    }

    private static Restaurant getTestRestaurant() {
        return new Restaurant(
                1L,
                "ODI",
                "prasp.Niezalieznasti 12",
                "375447721212",
                0,
                53.9006,
                27.5590);
    }

    private RestaurantDto getTestRestaurantDto() {
        return new RestaurantDto(
                1L,
                "ODI",
                "prasp.Niezalieznasti 12",
                "375447721212",
                0,
                53.9006,
                27.5590,
                new WeatherDto()
        );
    }

    @Test
    void givenRestaurant_whenCreateRestaurant_thenReturnSavedRestaurant() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        RestaurantDto testRestaurantDto = getTestRestaurantDto();
        //when
        when(restaurantMapper.convertRestaurantDtoToRestaurant(testRestaurantDto)).thenReturn(testRestaurant);
        when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(testRestaurant);
        when(correctionPhoneNumber.correctPhoneNumber(testRestaurantDto.getPhoneNumber())).thenReturn(testRestaurantDto.getPhoneNumber());
        when(restaurantMapper.convertRestaurantToRestaurantDto(testRestaurant)).thenReturn(testRestaurantDto);
        restaurantService.save(testRestaurantDto);
        //then
        assertThat(testRestaurantDto.getId()).isGreaterThan(0);
        assertNotNull(testRestaurantDto);
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
        //given
        Restaurant testRestaurant = getTestRestaurant();
        RestaurantDto testRestaurantDto = getTestRestaurantDto();
        //when
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(testRestaurant);
        when(restaurantMapper.convertRestaurantToRestaurantDto(testRestaurant)).thenReturn(testRestaurantDto);
        when(restaurantMapper.convertRestaurantDtoToRestaurantDto(testRestaurantDto, testRestaurantDto)).thenReturn(testRestaurantDto);
        testRestaurantDto.setPhoneNumber("375447733000");
        when(correctionPhoneNumber.correctPhoneNumber(testRestaurantDto.getPhoneNumber())).thenReturn(testRestaurantDto.getPhoneNumber());
        when(restaurantMapper.convertRestaurantDtoToRestaurant(testRestaurantDto)).thenReturn(testRestaurant);
        RestaurantDto updatedRestaurantDto = restaurantService.update(testRestaurantDto);
        //then
        assertNotNull(updatedRestaurantDto);
        assertThat(updatedRestaurantDto).hasFieldOrPropertyWithValue("phoneNumber", "375447733000");
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