package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.ReviewDto;
import com.reservation_restaurants_service.entity.Restaurant;
import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review convertReviewDtoToReview(ReviewDto reviewDto,
                                           Restaurant restaurant,
                                           User user) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setRestaurant(restaurant);
        review.setUser(user);
        review.setReview(reviewDto.getReview());
        return review;
    }

    public ReviewDto convertReviewToReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReview(review.getReview());
        return reviewDto;
    }
}