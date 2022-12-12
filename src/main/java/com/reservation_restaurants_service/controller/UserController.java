package com.reservation_restaurants_service.controller;


import com.reservation_restaurants_service.configuration.jwt.JwtProvider;
import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.service.UserService;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController

public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    public UserController(UserService userService, JwtProvider jwtProvider, UserMapper userMapper) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
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
    public ResponseEntity<UserDto> findById(@PathVariable("userId") Long userId) {
        UserDto userById = userService.findUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping("/user/")
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

//    @GetMapping("/logout")
//	public ResponseEntity<UserDto> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		if (auth != null) {
//			response.getHeader("username", auth.getName());
//			resp.put("session, lastAccessedTime", session.getLastAccessedTime());
//			new SecurityContextLogoutHandler().logout(request, response, auth);
//		}
//
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}
}