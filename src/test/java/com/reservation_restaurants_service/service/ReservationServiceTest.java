package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.Status;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.repository.ReservationRepository;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.ReservationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReservationMapper reservationMapper;

    private Restaurant restaurant;
    private  Status status;
    private User user;

    @BeforeEach
    void setUp() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationService(reservationRepository, userRepository, reservationMapper, restaurantRepository);
    }

    private static Restaurant getTestRestaurant() {
        return new Restaurant(1L, "ODI", "prasp.Niezalieznasti 12", "375447721212", 0, 0, 0);
    }
    private static User getTestUser() {
        return new User(1L,
                "Victor",
                "Mit",
                "ViMit",
                "v.mit@gmail.com",
                "12345678",
                "375297658912",
                UserRole.USER,
                UserStatus.ACTIVE);
    }

//    @Test
//    void givenReservationToUser_whenCreateReservation_thenReturnSavedReservation() throws Exception {
//        Reservation testReservation = new Reservation(1L, restaurant.getId(), 2, user.getId());
//
//    }

    @Test
    void findReservationById() {
    }

    @Test
    void findAllReservations() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findAllReservationsByUserId() {
    }

    @Test
    void findAllReservationsByRestaurantId() {
    }

    @Test
    void setStatusToReservation() {
    }
}