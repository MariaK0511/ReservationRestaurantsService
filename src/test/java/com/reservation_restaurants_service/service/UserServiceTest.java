package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
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
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CorrectionPhoneNumber correctionPhoneNumber;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userMapper = Mockito.mock(UserMapper.class);
        correctionPhoneNumber = Mockito.mock(CorrectionPhoneNumber.class);
        userService = new UserService(userRepository, userMapper, null, null, correctionPhoneNumber);
    }

    private static User getTestUser() {
        return new User(
                1L,
                "Mike",
                "Smith",
                "MikeSmith",
                "m.smith@gmail.com",
                "12345678",
                "375297658912",
                UserRole.USER,
                UserStatus.ACTIVE);
    }

    private static UserDto getTestUserDto() {
        return new UserDto(
                1L,
                "Mike",
                "Smith",
                "MikeSmith",
                "m.smith@gmail.com",
                "12345678",
                "375297658912",
                UserRole.USER);
    }

    @Test
    void givenUser_whenCreateUser_thenReturnSavedUser() throws Exception {
        //given
        User testUser = getTestUser();
        UserDto testUserDto = getTestUserDto();
        //when
        when(userMapper.convertUserDtoToUser(testUserDto)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.convertUserToUserDto(testUser)).thenReturn(testUserDto);
        userService.save(testUserDto);
        //then
        assertEquals(1L, testUserDto.getId());
        assertThat(testUserDto.getId()).isGreaterThan(0);
        assertNotNull(testUserDto);
    }

    @Test
    void givenUserId_whenGetUserId_thenReturnUser() throws Exception {
        //given
        User testUser = getTestUser();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        //then
        userService.findUserById(testUser.getId());
        assertThat(testUser.getId()).isEqualTo(1L);
        assertNotNull(testUser);
    }

    @Test
    void givenListOfUsers_whenGetAllUsers_thenReturnUserList() throws NullPointerException {
        //given
        User testUser = getTestUser();
        List<User> users = new ArrayList<>();
        //when
        users.add(testUser);
        //then
        when(userRepository.findAll()).thenReturn(users);
        userService.findAllUsers();
        assertThat(users.size()).isGreaterThan(0);
        assertNotNull(users);
    }

    @Test
    void givenUser_whenUpdateUser_thenReturnUpdatedUser() throws NullPointerException {
        //given
        User testUser = getTestUser();
        UserDto testUserDto = getTestUserDto();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.convertUserToUserDto(testUser)).thenReturn(testUserDto);
        when(userMapper.convertUserDtoToUserDto(testUserDto, testUserDto)).thenReturn(testUserDto);
        testUserDto.setPhoneNumber("375294448231");
        when(correctionPhoneNumber.correctPhoneNumber(testUserDto.getPhoneNumber())).thenReturn(testUserDto.getPhoneNumber());
        when(userMapper.convertUserDtoToUser(testUserDto)).thenReturn(testUser);
        UserDto updatedUserDto = userService.update(testUserDto);
        //then
        assertNotNull(updatedUserDto);
        assertThat(updatedUserDto).hasFieldOrPropertyWithValue("phoneNumber", "375294448231");
    }

    @Test
    void givenUser_whenDeleteUser_thenUserNotExist() throws Exception {
        //given
        User testUser = getTestUser();
        List<User> users = new ArrayList<>();
        //when
        users.add(testUser);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Optional<User> savedUser = Optional.of(testUser);
        savedUser.ifPresent(value -> userService.delete(value.getId()));
    }

    @Test
    void givenUser_whenAddRoleToUser_returnUserWithRole() throws Exception {
        //given
        User testUser = getTestUser();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        testUser.setUserRole(UserRole.MANAGER);
        userService.setRoleToUser(testUser.getId(), UserRole.MANAGER);
        //then
        assertThat(testUser).hasFieldOrPropertyWithValue("userRole", UserRole.MANAGER);
    }
}