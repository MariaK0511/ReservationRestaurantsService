package com.reservation_restaurants_service.configuration.jwt;


import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

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
                user.getUserStatus().equals(UserStatus.ACTIVE),
                mapToGrantedAuthorities(user.getUserRole()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserRole userRole) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + userRole);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(grantedAuthority);
        return grantedAuthorityList;
    }
}