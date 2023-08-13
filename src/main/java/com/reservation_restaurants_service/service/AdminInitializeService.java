package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Reservation;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.Status;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.repository.ReservationRepository;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.ReviewRepository;
import com.reservation_restaurants_service.repository.UserRepository;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializeService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AdminInitializeService(UserRepository userRepository, RestaurantRepository restaurantRepository, ReservationRepository reservationRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void initAdmin() {
        User user = new User();
        user.setUserRole(UserRole.ADMIN);
        user.setUsername("Mary");
        user.setSurname("Mikh");
        user.setNickname("MaryMikh");
        user.setEmail("mary@gmail.com");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setPhoneNumber("80448878709");
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Patrick Pub");
        restaurant.setAddress("str.Hiercena, 4");
        restaurant.setPhoneNumber("+375296199865");
        restaurant.setRating(0);
        restaurant.setLat(53.893009);
        restaurant.setLon(27.567444);
        restaurantRepository.save(restaurant);
        Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setCreationTime(LocalDateTime.now());
        reservation.setGuests(2);
        reservation.setStatus(Status.RESERVED);
        reservation.setTimeOfStatusChange(LocalDateTime.now());
        reservation.setUser(user);
        reservationRepository.save(reservation);
        Review review = new Review();
        review.setReview("Delicious meat dishes");
        review.setUser(user);
        review.setRestaurant(restaurant);
        reviewRepository.save(review);
    }
}