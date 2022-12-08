package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.exception.UserNotFoundException;
import com.reservation_restaurants_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // а нужна ли она?
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDto save(UserDto userDto) {
        userRepository.save(userDto);
        return userDto;
    }

    public UserDto findUserById(Long id) {
        Optional<UserDto> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll();
    }

    public UserDto update(UserDto userDto) {
        UserDto editedUser = findUserById(userDto.getId());
        editedUser.setName(userDto.getName());
        editedUser.setSurname(userDto.getSurname());
        editedUser.setEmail(userDto.getEmail());
        editedUser.setPassword(userDto.getPassword());
        editedUser.setPhoneNumber(userDto.getPhoneNumber());
        save(editedUser);
        return editedUser;
    }

    public void delete(Long id) {
        Optional<UserDto> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            userRepository.delete(userById.get());
        } else {
            throw new UserNotFoundException();
        }
    }
}