package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.User;
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

    @BeforeEach
    void setUp() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationService(reservationRepository, userRepository, reservationMapper, restaurantRepository);
    }

//    void givenReservationToUser_whenCreateReservation_thenReturnSavedReservation() throws Exception {
//        Reservation reservation = new Reservation();
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        assertThat(user.getId()).isGreaterThan(0);
//        userService.save(user);
//        assertNotNull(user);
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