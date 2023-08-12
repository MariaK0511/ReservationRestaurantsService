package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    private CorrectionPhoneNumber correctionPhoneNumber;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, userMapper, null, null, correctionPhoneNumber);
    }

    @Test
    void givenUser_whenCreateUser_thenReturnSavedUser() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", "12344L", UserRole.USER, UserStatus.ACTIVE, null, null);
        when(userRepository.save(any(User.class))).thenReturn(user);
      //  userService.save(user);
        assertThat(user.getId()).isGreaterThan(0);
        assertNotNull(user);
    }

    @Test
    void givenUserId_whenGetUserId_thenReturnUser() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", "777", UserRole.USER, UserStatus.ACTIVE, null, null);
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
                "email", "password", "8766", UserRole.USER, UserStatus.ACTIVE, null, null);
        user.setUsername("test username");
        user.setSurname("test surname");
        user.setEmail("test email");
        user.setPassword("test password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto updatedUser = new UserDto(1L, "test username", "test surname", "test nickname",
                "email", "password", "98", UserRole.USER);
        UserDto resultUser = userService.update(updatedUser);
        assertThat(updatedUser.getUsername()).isEqualTo(resultUser.getUsername());
        assertThat(updatedUser.getSurname()).isEqualTo(resultUser.getSurname());
        assertThat(updatedUser.getNickname()).isEqualTo(resultUser.getNickname());
        assertThat(updatedUser.getEmail()).isEqualTo(resultUser.getEmail());
        assertThat(updatedUser.getPassword()).isEqualTo(resultUser.getPassword());
        assertNotNull(resultUser);
        assertEquals(updatedUser.getId(), resultUser.getId());
    }

    @Test
    void givenUser_whenDeleteUser() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", "987", UserRole.USER, UserStatus.ACTIVE, null, null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> savedUser = Optional.of(user);
        savedUser.ifPresent(value -> userService.delete(value.getId()));
    }

    @Test
    void givenUser_whenAddRoleToUser_returnUserWithRole() throws Exception {
        User user = new User(1L, "username", "surname", "nickname",
                "email", "password", "98", UserRole.USER, UserStatus.ACTIVE, null, null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user.setUserRole(UserRole.MANAGER);
        userService.setRoleToUser(user.getId(), UserRole.MANAGER);
        when(userRepository.save(any(User.class))).thenReturn(user);
    }
}