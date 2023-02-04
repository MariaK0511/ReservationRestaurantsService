package com.reservation_restaurants_service.controller;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.service.ReservationService;
import com.reservation_restaurants_service.service.UserService;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(tags = "User")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ReservationService reservationService;

    public UserController(UserService userService, UserMapper userMapper, ReservationService reservationService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.reservationService = reservationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserDto userDto) {
        userService.registration(userMapper.convertUserDtoToUser(userDto));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        String jwt = userService.loginByEmailAndByPassword(userDto.getEmail(), userDto.getPassword());
        if (jwt != null) {
            return ResponseEntity.ok(jwt);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "Find user")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") Long userId) {
        UserDto userById = userService.findUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping("/users")
    @ApiOperation(value = "Show users")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAllUsers();
        return ok(users);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable("userId") Long userId,
                                          @RequestBody UserDto userDto) {
        userDto.setId(userId);
        UserDto user = userService.update(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/reservations")
    public ResponseEntity<List<ReservationDto>> showUsersReservations(@PathVariable("userId") long userId) {
        List<ReservationDto> reservationDtoList = reservationService.findAllReservationsByUserId(userId);
        return ok(reservationDtoList);
    }

    @PutMapping("/user/{userId}/role")
    public ResponseEntity<UserDto> setUserRole(@PathVariable("userId") Long userId,
                                               @RequestBody UserDto userDto){
        UserDto userWithRole = userService.setRoleToUser(userId, userDto.getUserRole());
        return ok(userWithRole);
    }
}