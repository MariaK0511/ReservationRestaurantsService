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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
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
    @Mock
    private WeatherDto weatherDto;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        restaurantMapper = Mockito.mock(RestaurantMapper.class);
        correctionPhoneNumber = Mockito.mock(CorrectionPhoneNumber.class);
        weatherService = Mockito.mock(WeatherService.class);
        weatherDto = Mockito.mock(WeatherDto.class);
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
                weatherDto
        );
    }

    @Test
    void successfulSavedRestaurantIfInputRestaurantDataIsCorrect() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        RestaurantDto testRestaurantDto = getTestRestaurantDto();
        //when
        when(restaurantMapper.convertRestaurantDtoToRestaurant(testRestaurantDto)).thenReturn(testRestaurant);
        when(correctionPhoneNumber.correctPhoneNumber(testRestaurantDto.getPhoneNumber())).thenReturn(testRestaurantDto.getPhoneNumber());
        when(restaurantMapper.convertRestaurantToRestaurantDto(testRestaurant)).thenReturn(testRestaurantDto);
        RestaurantDto savedRestaurant = restaurantService.save(testRestaurantDto);
        //then
        assertNotNull(savedRestaurant);
        assertEquals(1L, savedRestaurant.getId());
    }

    @Test
    void successfulReturnRestaurantById() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        RestaurantDto testRestaurantDto = getTestRestaurantDto();
        //when
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(restaurantMapper.convertRestaurantToRestaurantDto(testRestaurant)).thenReturn(testRestaurantDto);
        when(weatherService.getWeather(testRestaurantDto.getLat(), testRestaurantDto.getLon())).thenReturn(weatherDto);
        RestaurantDto foundRestaurant = restaurantService.findRestaurantById(testRestaurant.getId(), true);
        //then
        assertNotNull(foundRestaurant);
        assertEquals(1L, foundRestaurant.getId());
    }

    @Test
    void successfulReturnListOfRestaurants() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        RestaurantDto testRestaurantDto = getTestRestaurantDto();
        List<Restaurant> restaurants = new ArrayList<>();
        //when
        restaurants.add(testRestaurant);
        when(restaurantMapper.convertRestaurantToRestaurantDto(testRestaurant)).thenReturn(testRestaurantDto);
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        List<RestaurantDto> foundRestaurants = restaurantService.findAllRestaurants();
        //then
        assertNotNull(foundRestaurants);
        assertThat(foundRestaurants.size()).isGreaterThan(0);
    }

    @Test
    void successfulUpdateIncomingRestaurant() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        RestaurantDto testRestaurantDto = getTestRestaurantDto();
        //when
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(restaurantMapper.convertRestaurantToRestaurantDto(testRestaurant)).thenReturn(testRestaurantDto);
        when(restaurantMapper.convertRestaurantDtoToRestaurantDto(testRestaurantDto, testRestaurantDto)).thenReturn(testRestaurantDto);
        testRestaurantDto.setPhoneNumber("375447733000");
        when(correctionPhoneNumber.correctPhoneNumber(testRestaurantDto.getPhoneNumber())).thenReturn(testRestaurantDto.getPhoneNumber());
        when(restaurantMapper.convertRestaurantDtoToRestaurant(testRestaurantDto)).thenReturn(testRestaurant);
        RestaurantDto updatedRestaurantDto = restaurantService.update(testRestaurantDto);
        //then
        assertNotNull(updatedRestaurantDto);
        assertEquals("375447733000", updatedRestaurantDto.getPhoneNumber());
    }

    @Test
    void successfulDeleteRestaurantById() throws Exception {
        //given
        Restaurant testRestaurant = getTestRestaurant();
        //when
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        restaurantService.delete(testRestaurant.getId());
        //then
        verify(restaurantRepository).delete(testRestaurant);
    }
}