package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.exception.UserNotFoundException;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.assertj.core.api.Assertions.assertThat;

import javax.crypto.Mac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;


    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, userMapper, null, null);
    }

    @Test
    void givenUser_whenCreateUser_thenReturnSavedUser() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", 12344L, UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertThat(user.getId()).isGreaterThan(0);
        userService.save(user);
        assertNotNull(user);
    }

    @Test
    void givenUserId_whenGetUserId_thenReturnUser() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", 12344L, UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.findUserById(user.getId());
        assertThat(user.getId()).isEqualTo(1L);
        assertNotNull(user);

    }

    @Test
    void givenListOfUsers_whenGetAllUsers_thenReturnUserList() throws NullPointerException {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);
        userService.findAllUsers();
        assertThat(users.size()).isGreaterThan(0);
        assertNotNull(users);
    }

    @Test
    void givenUser_whenUpdateUser_thenReturnUpdatedUser() throws NullPointerException {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", 12344L, UserRole.USER, UserStatus.ACTIVE);
        user.setUsername("test username");
        user.setSurname("test surname");
        user.setEmail("test email");
        user.setPassword("test password");
        User updatedUser = userRepository.save(user);
        UserDto resultUser = userService.update(userMapper.convertUserToUserDto(updatedUser));
       userService.update(userMapper.convertUserToUserDto(updatedUser));
        assertThat(resultUser.getUsername()).isEqualTo(updatedUser.getUsername());
        assertThat(resultUser.getSurname()).isEqualTo(updatedUser.getSurname());
        assertThat(resultUser.getNickname()).isEqualTo(updatedUser.getNickname());
        assertThat(resultUser.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(resultUser.getPassword()).isEqualTo(updatedUser.getPassword());
        assertNotNull(resultUser);
        assertEquals(resultUser.getId(), updatedUser.getId());

    }

    @Test
    void givenUser_whenDeleteUser() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", 12344L, UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.delete(user.getId());
    }

    @Test
    void givenUser_whenAddRoleToUser_returnUserWithRole() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", 12344L, UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user.setUserRole(UserRole.MANAGER);
        userService.setRoleToUser(user.getId(), UserRole.MANAGER);
        when(userRepository.save(any(User.class))).thenReturn(user);
    }
}