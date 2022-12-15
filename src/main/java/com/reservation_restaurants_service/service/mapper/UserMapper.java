package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setSurname(userDto.getSurname());
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setSurname(user.getSurname());
        userDto.setNickname(user.getNickname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    public UserDto convertUserDtoToUserDto(UserDto incomeUserDto, UserDto savedUserDto) {
        savedUserDto.setUsername(incomeUserDto.getUsername());
        savedUserDto.setSurname(incomeUserDto.getSurname());
        savedUserDto.setNickname(incomeUserDto.getNickname());
        savedUserDto.setPassword(incomeUserDto.getPassword());
        savedUserDto.setPhoneNumber(incomeUserDto.getPhoneNumber());
        return savedUserDto;
    }
}