package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.Status;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.exception.ReservationNotFoundException;
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
import static org.mockito.Mockito.*;
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
    void successfulSavedReservationIfInputReservationDataIsCorrect() {
        //given
        Reservation testReservation = getReservation();
        ReservationDto testReservationDto = getTestReservationDto();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(reservationMapper.convertReservationDtoToReservation(testReservationDto, testRestaurant, testUser)).thenReturn(testReservation);
        when(reservationMapper.convertReservationToReservationDto(testReservation)).thenReturn(testReservationDto);
        ReservationDto savedReservationDto = reservationService.save(testReservationDto, testRestaurant.getId(), testUser.getId());
        //then
        assertNotNull(savedReservationDto);
        assertEquals(1L, savedReservationDto.getId());
    }

    @Test
    void successfulReturnReservationById() {
        //given
        Reservation testReservation = getReservation();
        ReservationDto testReservationDto = getTestReservationDto();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(reservationMapper.convertReservationToReservationDto(testReservation)).thenReturn(testReservationDto);
        ReservationDto foundReservation = reservationService.findReservationById(testReservationDto.getId());
        //then
        assertNotNull(foundReservation);
        assertEquals(1L, foundReservation.getId());
    }

    @Test
    void throwExceptionIfReturnNonExistentReservationById() {
        //given
        Long nonExistentId = 100L;
        //when
        when(reservationRepository.existsById(nonExistentId)).thenReturn(false);
        //then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.findReservationById(nonExistentId));
    }

    @Test
    void successfulReturnListOfReservations() throws NullPointerException {
        //given
        Reservation testReservation = getReservation();
        ReservationDto testReservationDto = getTestReservationDto();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(testReservation);
        //when
        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationMapper.convertReservationToReservationDto(testReservation)).thenReturn(testReservationDto);
        List<ReservationDto> foundReservations = reservationService.findAllReservations();
        //then
        assertNotNull(foundReservations);
        assertThat(foundReservations.size()).isGreaterThan(0);
    }

    @Test
    void successfulUpdateIncomingReservation() {
        //given
        Reservation incomingReservation = getReservation();
        ReservationDto incomingReservationDto = getTestReservationDto();
        incomingReservationDto.setGuests(5);
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(incomingReservation));
        when(reservationMapper.convertReservationToReservationDto(incomingReservation)).thenReturn(incomingReservationDto);
        ReservationDto updatedReservationDto = reservationService.update(incomingReservationDto, incomingReservationDto.getId());
        //then
        assertNotNull(updatedReservationDto);
        assertThat(updatedReservationDto.getGuests()).isEqualTo(5);
    }

    @Test
    void throwExceptionIfUpdateNonExistentReservationById() {
        //given
        ReservationDto nonExistentReservation = new ReservationDto();
        nonExistentReservation.setId(100L);
        //when
        when(reservationRepository.existsById(nonExistentReservation.getId())).thenReturn(false);
        //then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.update(nonExistentReservation, nonExistentReservation.getId()));
    }

    @Test
    void successfulDeleteReservationById() {
        //given
        Reservation testReservation = getReservation();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        reservationService.delete(testReservation.getId());
        //then
        verify(reservationRepository).delete(testReservation);
    }

    @Test
    void throwExceptionIfDeleteNonExistentReservationById() {
        //given
        Long nonExistentId = 100L;
        //when
        when(reservationRepository.existsById(nonExistentId)).thenReturn(false);
        //then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.delete(nonExistentId));
    }

    @Test
    void getAllReservationsByUserId() {
        //given
        Reservation testReservation = getReservation();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(testReservation);
        //when
        when(reservationRepository.findByUserId(1L)).thenReturn(reservations);
        List<ReservationDto> foundReservations = reservationService.findAllReservationsByUserId(testUser.getId());
        //then
        assertNotNull(foundReservations);
        assertEquals(1, foundReservations.size());
    }

    @Test
    void getAllReservationsByRestaurantId() {
        //given
        Reservation testReservation = getReservation();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(testReservation);
        //when
        when(reservationRepository.findByRestaurantId(1L)).thenReturn(reservations);
        List<ReservationDto> foundReservations = reservationService.findAllReservationsByRestaurantId(testRestaurant.getId());
        //then
        assertNotNull(foundReservations);
        assertEquals(1, foundReservations.size());
    }

    @Test
    void successfulReturnReservationWithStatus() {
        //given
        Reservation testReservation = getReservation();
        ReservationDto testReservationDto = getTestReservationDto();
        //when
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(reservationMapper.convertReservationToReservationDto(testReservation)).thenReturn(testReservationDto);
        ReservationDto reservationWithStatus = reservationService.setStatusToReservation(testReservationDto.getId(), Status.ACTIVE);
        //then
        assertEquals(Status.ACTIVE, reservationWithStatus.getStatus());
    }

    @Test
    void throwExceptionIfAddStatusNonExistentReservation() {
        //given
        long nonExistentId = 100L;
        //when
        when(reservationRepository.existsById(nonExistentId)).thenReturn(false);
        //then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.setStatusToReservation(nonExistentId, Status.ACTIVE));
    }
}