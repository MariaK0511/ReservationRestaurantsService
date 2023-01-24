package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.configuration.jwt.GenerateJWTUser;
import com.reservation_restaurants_service.configuration.jwt.JwtProvider;
import com.reservation_restaurants_service.dto.UserDto;
import com.reservation_restaurants_service.entity.User;
import com.reservation_restaurants_service.enums.UserRole;
import com.reservation_restaurants_service.enums.UserStatus;
import com.reservation_restaurants_service.exception.UserNotFoundException;
import com.reservation_restaurants_service.repository.UserRepository;
import com.reservation_restaurants_service.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final JwtProvider jwtProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CorrectionPhoneNumber correctionPhoneNumber;

    public UserService(UserRepository userRepository, UserMapper userMapper, JwtProvider jwtProvider, BCryptPasswordEncoder bCryptPasswordEncoder, CorrectionPhoneNumber correctionPhoneNumber) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.correctionPhoneNumber = correctionPhoneNumber;
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
                return jwtProvider.generationToken(user.getEmail(), user.getUserRole());
            }
            return null;
        }
        return null;
    }

    public void registration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPhoneNumber(correctionPhoneNumber.correctPhoneNumber(user.getPhoneNumber()));
        userRepository.save(user);
        logger.info("registration {} was successful", user.getUsername());
    }

    public UserDto save(User user) {
        userRepository.save(user);
        logger.info("user {} was saved", user.getUsername());
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
        user.setPhoneNumber(correctionPhoneNumber.correctPhoneNumber(user.getPhoneNumber()));
        save(user);
        logger.info("user {} was updated successfully", user.getNickname());
        return savedUserDto;
    }

    public void delete(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            userRepository.delete(userById.get());
            logger.info("user {} was deleted", userById.get().getNickname());
        } else {
            logger.warn("such user couldn't find");
            throw new UserNotFoundException();
        }
    }

    public UserDto setRoleToUser(long id, UserRole userRole) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            User user = userById.get();
            user.setUserRole(userRole);
            userRepository.save(user);
            logger.info("role {} for {} was set up", user.getUserRole(), user.getNickname());
            return userMapper.convertUserToUserDto(user);
        }
        throw new UserNotFoundException();
    }
}