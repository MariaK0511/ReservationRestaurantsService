package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.repository.UserRepository;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializeService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AdminInitializeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initAdmin() {
        User user = new User();
        user.setUserRole(UserRole.ADMIN);
        user.setUsername("Mary");
        user.setSurname("Mikh");
        user.setNickname("MaryMikh");
        user.setEmail("mary@gmail.com");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setPhoneNumber("80448878709");
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }
}