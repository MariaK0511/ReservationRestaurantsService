package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.exception.UserNotFoundException;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional // а нужна ли она?
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDto save(UserDto userDto) {
        User user = userMapper.convertUserDtoToUser(userDto);
        userRepository.save(user);
        return userMapper.convertUserToUserDto(user);
    }

    public UserDto findUserById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            return userMapper.convertUserToUserDto(userById.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertUserToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto update(UserDto incomeUserDto) {
        UserDto savedUserDto = findUserById(incomeUserDto.getId());
        savedUserDto = userMapper.convertUserDtoToUserDto(incomeUserDto, savedUserDto);
        save(savedUserDto);
        return savedUserDto;
    }

    public void delete(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            userRepository.delete(userById.get());
        } else {
            throw new UserNotFoundException();
        }
    }
}