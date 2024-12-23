package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.ReviewDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.exception.ResourceNotFoundException;
import com.reservation_restaurants_service.repository.RestaurantRepository;
import com.reservation_restaurants_service.repository.ReviewRepository;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.ReviewMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReviewDto addReviewToRestaurant(ReviewDto reviewDto, long userId, long restaurantId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        Review review = reviewMapper.convertReviewDtoToReview(reviewDto, restaurant.get(), user.get());
        review.setReview(reviewDto.getReview());
        reviewRepository.save(review);
        logger.info("{} added review to {}", user.get().getNickname(), restaurant.get().getName());
        return reviewMapper.convertReviewToReviewDto(review);
    }

    public ReviewDto update(ReviewDto reviewDto, long id) {
        Optional<Review> reviewById = reviewRepository.findById(id);
        if (reviewById.isPresent()) {
            Review editedReview = reviewById.get();
            editedReview.setReview(reviewDto.getReview());
            reviewRepository.save(editedReview);
            logger.info("review  was updated");
            return reviewMapper.convertReviewToReviewDto(editedReview);
        }
        throw ResourceNotFoundException.of(Review.class, id);
    }

    public void delete(Long id) {
        Optional<Review> reviewById = reviewRepository.findById(id);
        if (reviewById.isPresent()) {
            reviewRepository.delete(reviewById.get());
            logger.info("{} was deleted", reviewById.get().getReview());
        } else {
            throw ResourceNotFoundException.of(Review.class, id);
        }
    }

    public List<ReviewDto> getAllReviewsByUserId(long userId) {
        return reviewRepository.findByUserId(userId)
                               .stream()
                               .map(reviewMapper::convertReviewToReviewDto)
                               .collect(Collectors.toList());
    }

    public List<ReviewDto> getAllReviewsByRestaurantId(long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId)
                               .stream()
                               .map(reviewMapper::convertReviewToReviewDto)
                               .collect(Collectors.toList());
    }
}