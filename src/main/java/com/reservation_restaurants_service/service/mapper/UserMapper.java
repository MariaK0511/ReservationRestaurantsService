package com.reservation_restaurants_service.service.mapper;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User convertUserDtoToUser(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getUsername(),
                userDto.getSurname(),
                userDto.getNickname(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getPhoneNumber(), null, null, null);
    }

    public UserDto convertUserToUserDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getSurname(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber());
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