package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
