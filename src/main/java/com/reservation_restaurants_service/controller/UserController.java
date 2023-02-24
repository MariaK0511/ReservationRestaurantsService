package com.reservation_restaurants_service.controller;

import com.reservation_restaurants_service.dto.ReservationDto;
import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.service.ReservationService;
import com.reservation_restaurants_service.service.UserService;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "User registration", notes = "Creating and adding user in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "Registration failed")
    })
    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserDto userDto) {
        userService.registration(userMapper.convertUserDtoToUser(userDto));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }
    @ApiOperation(value = "User login", notes = "User login via token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "User login failed")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        String jwt = userService.loginByEmailAndByPassword(userDto.getEmail(), userDto.getPassword());
        if (jwt != null) {
            return ResponseEntity.ok(jwt);
        }
        return ResponseEntity.badRequest().build();
    }
    @ApiOperation(value = "Find user by id", notes = "Return a user as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "The user is not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") Long userId) {
        UserDto userById = userService.findUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all users", notes = "Return all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "The users are not found")
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAllUsers();
        return ok(users);
    }

    @ApiOperation(value = "Update user by id", notes = "Return updated user as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "User update failed")
    })
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable("userId") Long userId,
                                          @RequestBody UserDto userDto) {
        userDto.setId(userId);
        UserDto user = userService.update(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "Deleting user failed")
    })
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get all user's reservations", notes = "Return user's reservations as per the user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "The user's reservations are not found")
    })
    @GetMapping("/user/{userId}/reservations")
    public ResponseEntity<List<ReservationDto>> showUsersReservations(@PathVariable("userId") long userId) {
        List<ReservationDto> reservationDtoList = reservationService.findAllReservationsByUserId(userId);
        return ok(reservationDtoList);
    }

    @ApiOperation(value = "Set user role by id", notes = "Return user role as per the user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code=404, message = "The user's role is not found")
    })
    @PutMapping("/user/{userId}/role")
    public ResponseEntity<UserDto> setUserRole(@PathVariable("userId") Long userId,
                                               @RequestBody UserDto userDto){
        UserDto userWithRole = userService.setRoleToUser(userId, userDto.getUserRole());
        return ok(userWithRole);
    }
}