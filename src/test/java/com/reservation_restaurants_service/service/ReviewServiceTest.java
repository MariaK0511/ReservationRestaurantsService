package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReviewDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.exception.ResourceNotFoundException;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.ReviewRepository;
import com.reservation_restaurants_service.repository.UserRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
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
    void successfulSavedReviewIfInputReviewDataIsCorrect() {
        //given
        Review testReview = getTestReview();
        ReviewDto testReviewDto = getTestReviewDto();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(reviewMapper.convertReviewDtoToReview(testReviewDto, testRestaurant, testUser)).thenReturn(testReview);
        when(reviewMapper.convertReviewToReviewDto(testReview)).thenReturn(testReviewDto);
        ReviewDto savedReviewDto = reviewService.addReviewToRestaurant(testReviewDto, testUser.getId(), testRestaurant.getId());
        //then
        assertNotNull(savedReviewDto);
        assertThat(savedReviewDto.getId()).isEqualTo(1L);
    }

    @Test
    void getAllReviewsByUserId() {
        //given
        Review testReview = getTestReview();
        List<Review> reviews = new ArrayList<>();
        reviews.add(testReview);
        //when
        when(reviewRepository.findByUserId(1L)).thenReturn(reviews);
        List<ReviewDto> foundReviews = reviewService.getAllReviewsByUserId(testUser.getId());
        //then
        assertNotNull(foundReviews);
        assertEquals(1, foundReviews.size());
    }

    @Test
    void getAllReviewsByRestaurantId() {
        //given
        Review testReview = getTestReview();
        List<Review> reviews = new ArrayList<>();
        reviews.add(testReview);
        //when
        when(reviewRepository.findByRestaurantId(1L)).thenReturn(reviews);
        List<ReviewDto> foundReviews = reviewService.getAllReviewsByRestaurantId(testRestaurant.getId());
        //then
        assertNotNull(foundReviews);
        assertEquals(1, foundReviews.size());
    }

    @Test
    void successfulUpdateIncomingReview() {
        //given
        Review incomingReview = getTestReview();
        ReviewDto incomingReviewDto = getTestReviewDto();
        incomingReviewDto.setReview("bad service");
        //when
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(incomingReview));
        when(reviewMapper.convertReviewToReviewDto(incomingReview)).thenReturn(incomingReviewDto);
        ReviewDto updatedReviewDto = reviewService.update(incomingReviewDto, incomingReviewDto.getId());
        //then
        assertNotNull(updatedReviewDto);
        assertEquals("bad service", updatedReviewDto.getReview());
    }

    @Test
    void throwExceptionIfUpdateNonExistentReview() {
        //given
        ReviewDto nonExistentReview = new ReviewDto();
        nonExistentReview.setId(100L);
        //when
        when(reviewRepository.existsById(nonExistentReview.getId())).thenReturn(false);
        //then
        assertThrows(ResourceNotFoundException.class, () -> reviewService.update(nonExistentReview, nonExistentReview.getId()));
    }

    @Test
    void successfulDeleteReviewById() {
        //given
        Review testReview = getTestReview();
        //when
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        reviewService.delete(testReview.getId());
        //then
        verify(reviewRepository).delete(testReview);
    }

    @Test
    void throwExceptionIfDeleteNonExistentReviewById() {
        //given
        Long nonExistentId = 100L;
        //when
        when(reviewRepository.existsById(nonExistentId)).thenReturn(false);
        //then
        assertThrows(ResourceNotFoundException.class, () -> reviewService.delete(nonExistentId));
    }
}