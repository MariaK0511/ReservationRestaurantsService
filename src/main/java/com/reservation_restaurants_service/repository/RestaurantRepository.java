package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.dto.RestaurantDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDto, Long> {
}