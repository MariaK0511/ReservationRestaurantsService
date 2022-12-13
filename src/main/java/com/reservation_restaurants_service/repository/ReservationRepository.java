package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "from User where id = : userId")
    List<Reservation> findByUserId(long userId);

    @Query(value = "from Reservation where creationTime = : creationTime")
    Reservation findReservationByCreationTime(LocalDateTime creationTime);

    @Query(value = "from Restaurant where id = : restaurantId")
    List<Reservation> findByRestaurantId(long restaurantId);

}