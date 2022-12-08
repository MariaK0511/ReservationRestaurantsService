package com.reservation_restaurants_service.repository;

import com.reservation_restaurants_service.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDto,Long> {

}