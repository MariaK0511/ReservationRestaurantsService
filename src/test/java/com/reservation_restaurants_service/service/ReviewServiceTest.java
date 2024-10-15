package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.ReviewRepository;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewMapper reviewMapper;


    @BeforeEach
    void setUp() {
        reviewRepository = Mockito.mock(ReviewRepository.class);
        reviewService = new ReviewService(reviewRepository, reviewMapper, userRepository, restaurantRepository);
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

    private static Restaurant getTestRestaurant() {
        return new Restaurant(1L, "ODI", "prasp.Niezalieznasti 12", "375447721212", 0, 0, 0);
    }

    Restaurant testRestaurant = getTestRestaurant();
    User testUser = getTestUser();

    private Review getTestReview() {
        return new Review(1L, "good service", testUser, testRestaurant);
    }

    @Test
    void givenReview_whenCreateReview_thenReturnSavedReview() throws Exception {
        //given
        Review testReview = getTestReview();
        //when
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(testReview);
        //then
        // assertEquals(1L, testReview.getId());
        assertThat(testReview.getId()).isGreaterThan(0);
        assertNotNull(testReview);
    }


}
