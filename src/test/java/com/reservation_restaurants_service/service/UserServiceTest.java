package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

   @Mock
    private UserRepository userRepository;

   @InjectMocks
    private UserService userService;

    private UserMapper userMapper;
    private UserDto userDto;
    private static User testUser;
//
//    @BeforeTestClass
//    public  void prepareTestData() {
//        testUser = new User();
//        testUser.setId(1);
//        testUser.setUsername("username");
//        testUser.setSurname("surname");
//        testUser.setNickname("nickname");
//        testUser.setEmail("n@");
//        testUser.setPassword("44tgf");
//        testUser.setPhoneNumber(3445);
//    }



    @Test
    void givenUser_whenCreateUser_thenReturnSavedUser() {
        User user = new User();
        user.setUsername("Username");
        user.setSurname("Surname");
        user.setNickname("Nickname");
        user.setEmail("Email");
        user.setPassword("Password");
        user.setPhoneNumber(445543);

       when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
       UserDto createdUser = userService.save(user);
       assertThat(createdUser.getId()).isSameAs(user.getId());
       verify(userRepository).save(user);

    }

    @Test
    void givenUserId_whenGetUserId_thenReturnUser() {
        User user = userRepository.findById(userDto.getId()).get();
        assertThat(user.getId()).isEqualTo(userDto.getId());
        userMapper.convertUserToUserDto(user);
    }

    @Test
    void givenListOfUsers_whenGetAllUsers_thenReturnUserList() {
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(userMapper::convertUserToUserDto)
                .collect(Collectors.toList());

        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    void givenUser_whenUpdateUser_thenReturnUpdatedUser(UserDto incomeUserDto) {
        UserDto savedUserDto = userService.findUserById(incomeUserDto.getId());
        savedUserDto = userMapper.convertUserDtoToUserDto(incomeUserDto, savedUserDto);
        User user = userMapper.convertUserDtoToUser(savedUserDto);
        userService.save(user);
        assertThat(incomeUserDto).isEqualTo(savedUserDto);
    }

    @Test
    void givenUser_whenDeleteUser(long id) {
        Optional<User> userById = userRepository.findById(id);
        userById.ifPresent(user -> userMapper.convertUserToUserDto(user));
        assertThat(userById).isEmpty();
    }

    @Test
    void givenUser_whenAddRoleToUser_returnUserWithRole(long id) {
        UserRole userRole = userDto.getUserRole();
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            User user = userById.get();
            user.setUserRole(userRole);
            userRepository.save(user);
            userMapper.convertUserToUserDto(user);
        }
        assertThat(userById.get().getUserRole()).isEqualTo(userRole);
    }
}