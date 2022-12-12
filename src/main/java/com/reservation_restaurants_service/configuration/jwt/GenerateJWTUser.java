package com.reservation_restaurants_service.configuration.jwt;


import com.reservation_restaurants_service.entity.Role;
import com.reservation_restaurants_service.entity.Status;
import com.reservation_restaurants_service.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateJWTUser {
    public static JWTUser create(User user) {
        return new JWTUser(
                user.getId(),
                user.getUsername(),
                user.getSurname(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoleList())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getTypeOfRole()))
                .collect(Collectors.toList());
    }
}
