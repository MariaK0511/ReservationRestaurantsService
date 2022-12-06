package com.reservation_restaurants_service.controller;


import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user) {
        // dto
        User saveUser = userService.save(user);
        return ok(saveUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable("userId") Long userId) {
        User userById = userService.findUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAllUsers();
        return ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable("userId") Long userId,
                                       @RequestBody UserDto userDto) {
        userDto.setId(userId);
        User user = userService.update(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
