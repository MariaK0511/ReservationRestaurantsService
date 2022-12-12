package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
 Optional <User> findByEmail(String email);
}