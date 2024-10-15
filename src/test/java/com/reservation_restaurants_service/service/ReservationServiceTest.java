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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Status status;
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

    Restaurant testRestaurant = getTestRestaurant();
    User testUser = getTestUser();

    private Reservation getReservation() {
        return new Reservation(1L,
                testRestaurant,
                LocalDateTime.now(),
                2,
                Status.ACTIVE,
                LocalDateTime.now(),
                getTestUser());
    }

    @Test
    void givenReservationToUser_whenCreateReservation_thenReturnSavedReservation() throws Exception {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
        //then
        assertEquals(1L, testReservation.getId());
        assertThat(testReservation.getId()).isGreaterThan(0);
        assertNotNull(testReservation);
    }


    @Test
    void givenReservationId_whenGetReservationId_thenReturnReservation() {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        //then
        reservationService.findReservationById(testReservation.getId());
        assertThat(testReservation.getId()).isEqualTo(1L);
        assertNotNull(testReservation);
    }

    @Test
    void givenListOfReservations_whenGetAllReservations_thenReturnReservationList() throws NullPointerException {
        //given
        Reservation testReservation = getReservation();
        List<Reservation> reservations = new ArrayList<>();
        //when
        reservations.add(testReservation);
        //then
        when(reservationRepository.findAll()).thenReturn(reservations);
        reservationService.findAllReservations();
        assertThat(reservations.size()).isGreaterThan(0);
        assertNotNull(reservations);
    }

    @Test
    void update() {
    }

    @Test
    void givenUser_whenDeleteReservation() throws Exception {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        //then
        Optional<Reservation> savedReservation = Optional.of(testReservation);
        savedReservation.ifPresent(reservation -> reservationService.delete(reservation.getId()));
    }

    @Test
    void givenReservationId_whenGetReservationByUserId_thenReturnUserReservation() {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        //then
        reservationService.findByUserId(testUser.getId());
        assertThat(testReservation.getId()).isEqualTo(1L);
        assertNotNull(testReservation);
    }

    @Test
    void findAllReservationsByUserId() {
        //given
        Reservation testReservation = getReservation();
        List<Reservation> reservations = new ArrayList<>();
        //when
        reservations.add(testReservation);
        //then
        when(reservationRepository.findAll()).thenReturn(reservations);
        reservationService.findAllReservationsByUserId(testUser.getId());
        assertThat(reservations.size()).isGreaterThan(0);
        assertNotNull(reservations);
    }

    @Test
    void findAllReservationsByRestaurantId() {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        //then
        reservationService.findAllReservationsByRestaurantId(testRestaurant.getId());
        assertThat(testReservation.getId()).isEqualTo(1L);
        assertNotNull(testReservation);
         
    }

    @Test
    void setStatusToReservation() {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        //then
        reservationService.setStatusToReservation(testReservation.getId(), Status.ACTIVE);
        assertThat(testReservation.getStatus()).isEqualTo(Status.ACTIVE);
    }
}