package com.reservation_restaurants_service.configuration.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JWTUser implements UserDetails {

    private final long id;
    private final String username;
    private final String surname;
    private final String nickname;
    private final String email;
    private final String password;
    private final long phoneNumber;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public JWTUser(long id, String username,
                   String surname, String nickname,
                   String email, String password,
                   long phoneNumber, boolean enabled,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}