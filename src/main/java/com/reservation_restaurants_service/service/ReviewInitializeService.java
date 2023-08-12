package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.Review;
import com.reservation_restaurants_service.repository.ReviewRepository;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ReviewInitializeService {

    private final ReviewRepository reviewRepository;

    public ReviewInitializeService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void initReview() {
        Review review = new Review();
        review.setReview("Delicious meat dishes");
        review.setUser(review.getUser());
        review.setRestaurant(review.getRestaurant());
        reviewRepository.save(review);
    }
}