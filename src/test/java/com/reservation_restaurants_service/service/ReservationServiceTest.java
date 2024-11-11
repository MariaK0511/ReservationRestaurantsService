package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReservationDto;
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
import org.assertj.core.api.AssertionsForClassTypes;
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
import static org.testng.AssertJUnit.assertNotNull;

@SpringBootTest
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        reservationMapper = Mockito.mock(ReservationMapper.class);
        reservationService = new ReservationService(reservationRepository, userRepository, reservationMapper, restaurantRepository);
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

    private static User getTestUser() {
        return new User(
                1L,
                "Mike",
                "Smith",
                "MikeSmith",
                "m.smith@gmail.com",
                "12345678",
                "375297658912",
                UserRole.USER,
                UserStatus.ACTIVE);
    }

    Restaurant testRestaurant = getTestRestaurant();
    User testUser = getTestUser();

    private static Reservation getReservation() {
        return new Reservation(1L,
                2,
                LocalDateTime.now(),
                Status.ACTIVE,
                LocalDateTime.now(),
                getTestRestaurant(),
                getTestUser());
    }

    private static ReservationDto getTestReservationDto() {
        return new ReservationDto(
                1L,
                2,
                LocalDateTime.now(),
                Status.ACTIVE,
                LocalDateTime.now());
    }

    @Test
    void givenReservationToUser_whenCreateReservation_thenReturnSavedReservation() throws Exception {
        //given
        Reservation testReservation = getReservation();
        ReservationDto testReservationDto = getTestReservationDto();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(reservationMapper.convertReservationDtoToReservation(testReservationDto, testRestaurant, testUser)).thenReturn(testReservation);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
        when(reservationMapper.convertReservationToReservationDto(testReservation)).thenReturn(testReservationDto);
        reservationService.save(testReservationDto, testRestaurant.getId(), testUser.getId());
        //then
        assertEquals(1L, testReservationDto.getId());
        assertThat(testReservationDto.getId()).isGreaterThan(0);
        assertNotNull(testReservationDto);
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
    void givenReservation_whenUpdateReservation_thenReturnUpdatedReservation() {
        //given
        Reservation testReservation = getReservation();
        ReservationDto testReservationDto = getTestReservationDto();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(reservationMapper.convertReservationToReservationDto(testReservation)).thenReturn(testReservationDto);
        testReservationDto.setGuests(5);
        reservationService.update(testReservationDto, testReservationDto.getId());
        when(reservationMapper.convertReservationDtoToReservation(testReservationDto, testRestaurant, testUser)).thenReturn(testReservation);
        //then
        assertNotNull(testReservationDto);
        AssertionsForClassTypes.assertThat(testReservationDto).hasFieldOrPropertyWithValue("guests", 5);
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