package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
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

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public User findUserById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User update(UserDto userDto) {
        User editedUser = findUserById(userDto.getId());
        editedUser.setName(userDto.getName());
        editedUser.setSurname(userDto.getSurname());
        editedUser.setEmail(userDto.getEmail());
        editedUser.setPassword(userDto.getPassword());
        editedUser.setPhoneNumber(userDto.getPhoneNumber());
        // где сейв
        return editedUser;
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