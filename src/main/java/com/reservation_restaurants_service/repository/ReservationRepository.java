package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(long userId);

    List<Reservation> findByRestaurantId(long restaurantId);
}