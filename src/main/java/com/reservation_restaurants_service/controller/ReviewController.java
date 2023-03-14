package com.reservation_restaurants_service.controller;

import com.reservation_restaurants_service.dto.ReviewDto;
import com.reservation_restaurants_service.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(tags = "Review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation(value = "Add review by restaurant id and user id", notes = "Creating and adding review in database")
    @PostMapping("/restaurant/{restaurantId}/user/{userId}/review")
    public ResponseEntity<ReviewDto> save(@RequestBody ReviewDto reviewDto,
                                          @PathVariable("userId") Long userId,
                                          @PathVariable("restaurantId") Long restaurantId) {
        ReviewDto saveReview = reviewService.addReviewToRestaurant(reviewDto, userId, restaurantId);
        return ok(saveReview);
    }

    @ApiOperation(value = "Update review by id", notes = "Return updated review as per the id")
    @PutMapping("/restaurant/review/{reviewId}")
    public ResponseEntity<ReviewDto> update(@RequestBody ReviewDto reviewDto,
                                            @PathVariable("reviewId") long reviewId) {
        reviewDto.setId(reviewId);
        ReviewDto editedReview = reviewService.update(reviewDto, reviewId);
        return new ResponseEntity<>(editedReview, OK);
    }

    @ApiOperation(value = "Delete review by id")
    @DeleteMapping("/restaurant/review/{reviewId}")
    public ResponseEntity<ReviewDto> delete(@PathVariable("reviewId") long reviewId) {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Get restaurant reviews", notes = "Return restaurant reviews")
    @GetMapping("/restaurant/{restaurantId}/reviews")
    public ResponseEntity<List<ReviewDto>> showReviewsOfRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        List<ReviewDto> reviewDtoList = reviewService.getAllReviewsByRestaurantId(restaurantId);
        return ok(reviewDtoList);
    }

    @ApiOperation(value = "Get user reviews", notes = "Return user reviews")
    @GetMapping("/user/{userId}/reviews")
    public ResponseEntity<List<ReviewDto>> showReviewsOfUser(@PathVariable("userId") Long userId) {
        List<ReviewDto> reviewDtoList = reviewService.getAllReviewsByUserId(userId);
        return ok(reviewDtoList);
    }
}