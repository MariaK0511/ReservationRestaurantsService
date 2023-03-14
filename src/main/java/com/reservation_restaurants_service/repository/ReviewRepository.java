package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(long userId);
    List<Review> findByRestaurantId(long restaurantId);

}
