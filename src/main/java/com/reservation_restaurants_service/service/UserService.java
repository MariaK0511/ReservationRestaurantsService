package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.configuration.jwt.GenerateJWTUser;
import com.reservation_restaurants_service.configuration.jwt.JwtProvider;
import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.Role;
import com.reservation_restaurants_service.entity.Status;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.exception.UserNotFoundException;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, UserMapper userMapper, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + email + "not found");
        }
        return GenerateJWTUser.create(user.get());
    }

    public String loginByEmailAndByPassword(String email, String password) {
        Optional<User> userByUsername = userRepository.findByEmail(email);
        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtProvider.generationToken(user.getEmail(), user.getRoleList());
            }
            return null;
        }
        return null;
    }

    public void registration(User user) {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setTypeOfRole("USER");
        roles.add(role);
        user.setRoleList(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(roles);
        user.setStatus(Status.ACTIVE);
        role.setUser(user);
        userRepository.save(user);
    }

    public UserDto save(User user) {
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
        User user = userMapper.convertUserDtoToUser(savedUserDto);
        save(user);
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