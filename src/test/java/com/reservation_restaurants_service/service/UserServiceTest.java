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
import static org.mockito.Mockito.verify;
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
    void successfulSavedUserIfInputUserDataIsCorrect() throws Exception {
        //given
        User testUser = getTestUser();
        UserDto testUserDto = getTestUserDto();
        //when
        when(userMapper.convertUserDtoToUser(testUserDto)).thenReturn(testUser);
        when(userMapper.convertUserToUserDto(testUser)).thenReturn(testUserDto);
        UserDto savedUserDto = userService.save(testUserDto);
        //then
        assertNotNull(savedUserDto);
        assertEquals(1L, savedUserDto.getId());
    }

    @Test
    void successfulReturnUserById() throws Exception {
        //given
        User testUser = getTestUser();
        UserDto testUserDto = getTestUserDto();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.convertUserToUserDto(testUser)).thenReturn(testUserDto);
        UserDto returnedUserDto = userService.findUserById(testUser.getId());
        //then
        assertNotNull(returnedUserDto);
        assertEquals(1L, returnedUserDto.getId());
    }

    @Test
    void successfulReturnListOfUsers() throws NullPointerException {
        //given
        User testUser = getTestUser();
        UserDto testUserDto = getTestUserDto();
        List<User> users = new ArrayList<>();
        users.add(testUser);
        //when
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.convertUserToUserDto(testUser)).thenReturn(testUserDto);
        List<UserDto> foundUsers = userService.findAllUsers();
        //then
        assertNotNull(foundUsers);
        assertThat(foundUsers.size()).isGreaterThan(0);
    }

    @Test
    void successfulUpdateIncomingUser() throws NullPointerException {
        //given
        User incomingUser = getTestUser();
        UserDto incomingUserDto = getTestUserDto();
        incomingUserDto.setPhoneNumber("375294448231");
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(incomingUser));
        when(userMapper.convertUserToUserDto(incomingUser)).thenReturn(incomingUserDto);
        when(userMapper.convertUserDtoToUserDto(incomingUserDto, incomingUserDto)).thenReturn(incomingUserDto);
        when(correctionPhoneNumber.correctPhoneNumber(incomingUserDto.getPhoneNumber())).thenReturn(incomingUserDto.getPhoneNumber());
        when(userMapper.convertUserDtoToUser(incomingUserDto)).thenReturn(incomingUser);
        UserDto updatedUserDto = userService.update(incomingUserDto);
        //then
        assertNotNull(updatedUserDto);
        assertThat(updatedUserDto.getPhoneNumber()).isEqualTo("375294448231");
    }

    @Test
    void successfulDeleteUserById() throws Exception {
        //given
        User testUser = getTestUser();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        userService.delete(testUser.getId());
        //then
        verify(userRepository).delete(testUser);
    }

    @Test
    void successfulReturnUserWithRole() throws Exception {
        //given
        User testUser = getTestUser();
        UserDto userWithOldRole = getTestUserDto();
        userWithOldRole.setUserRole(UserRole.MANAGER);
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.convertUserToUserDto(testUser)).thenReturn(userWithOldRole);
        UserDto userWithNewRole = userService.setRoleToUser(userWithOldRole.getId(), UserRole.MANAGER);
        //then
        assertEquals(UserRole.MANAGER, userWithNewRole.getUserRole());
    }
}