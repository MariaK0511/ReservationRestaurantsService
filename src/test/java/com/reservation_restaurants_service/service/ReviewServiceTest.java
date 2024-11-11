package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReviewDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.ReviewRepository;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.RestaurantMapper;
import com.reservation_restaurants_service.service.mapper.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReviewMapper reviewMapper;

    @BeforeEach
    void setUp() {
        reviewRepository = Mockito.mock(ReviewRepository.class);
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        reviewMapper = Mockito.mock(ReviewMapper.class);
        reviewService = new ReviewService(reviewRepository, reviewMapper, userRepository, restaurantRepository);
    }

    private static User getTestUser() {
        return new User(1L,
                "Mike",
                "Smith",
                "MikeSmith",
                "m.smith@gmail.com",
                "12345678",
                "375297658912",
                UserRole.USER,
                UserStatus.ACTIVE);
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

    private static ReviewDto getTestReviewDto() {
        return new ReviewDto(
                1L,
                "good service");
    }

    private Review getTestReview() {
        return new Review(
                1L,
                "good service",
                getTestUser(),
                getTestRestaurant());
    }

    Restaurant testRestaurant = getTestRestaurant();
    User testUser = getTestUser();

    @Test
    void givenReview_whenCreateReview_thenReturnSavedReview() throws Exception {
        //given
        Review testReview = getTestReview();
        ReviewDto testReviewDto = getTestReviewDto();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(reviewMapper.convertReviewDtoToReview(testReviewDto, testRestaurant, testUser)).thenReturn(testReview);
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(testReview);
        when(reviewMapper.convertReviewToReviewDto(testReview)).thenReturn(testReviewDto);
        reviewService.addReviewToRestaurant(testReviewDto, testUser.getId(), testRestaurant.getId());
        //then
        assertThat(testReviewDto.getId()).isEqualTo(1L);
        assertThat(testReviewDto.getId()).isGreaterThan(0);
        assertNotNull(testReviewDto);
    }

    @Test
    void givenReview_whenUpdateReview_thenReturnUpdatedReview() {
        //given
        Review testReview = getTestReview();
        ReviewDto testReviewDto = getTestReviewDto();
        //when
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        when(reviewMapper.convertReviewToReviewDto(testReview)).thenReturn(testReviewDto);
        testReviewDto.setReview("bad service");
        reviewService.update(testReviewDto, testReviewDto.getId());
        when(reviewMapper.convertReviewDtoToReview(testReviewDto, testRestaurant, testUser)).thenReturn(testReview);
        //then
        assertNotNull(testReview);
        assertThat(testReview).hasFieldOrPropertyWithValue("review",  "bad service");
    }

    @Test
    void givenListOfReviews_whenGetAllReviews_thenReturnReviewsByUserId() throws Exception {
        //given
        Review testReview = getTestReview();
        ReviewDto testReviewDto = getTestReviewDto();
        List<Review> reviews = new ArrayList<>();
        //when
        reviews.add(testReview);
        when(reviewMapper.convertReviewToReviewDto(testReview)).thenReturn(testReviewDto);
        when(reviewRepository.findByUserId(1L)).thenReturn(reviews);
        //then
        reviewService.getAllReviewsByUserId(testUser.getId());
        assertThat(testReview.getId()).isEqualTo(1L);
        assertThat(testReview.getId()).isGreaterThan(0);
        assertNotNull(testReview);
    }

    @Test
    void givenListOfReviews_whenGetAllReviews_thenReturnReviewsByRestaurantId() throws Exception {
        //given
        Review testReview = getTestReview();
        ReviewDto testReviewDto = getTestReviewDto();
        List<Review> reviews = new ArrayList<>();
        //when
        reviews.add(testReview);
        when(reviewMapper.convertReviewToReviewDto(testReview)).thenReturn(testReviewDto);
        when(reviewRepository.findByRestaurantId(1L)).thenReturn(reviews);
        reviewService.getAllReviewsByRestaurantId(testRestaurant.getId());
        //then
        assertThat(testReview.getId()).isEqualTo(1L);
        assertThat(testReview.getId()).isGreaterThan(0);
        assertNotNull(testReview);
    }

    @Test
    void givenReview_whenDeleteReview() throws Exception {
        //given
        Review testReview = getTestReview();
        List<Review> reviews = new ArrayList<>();
        //when
        reviews.add(testReview);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        //then
        reviewService.delete(testReview.getId());
        List<Review> deletedReview = reviewRepository.findAll();
        assertEquals(reviews.size() - 1, deletedReview.size());
    }
}