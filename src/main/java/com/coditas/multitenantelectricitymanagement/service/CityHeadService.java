package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.DuplicateResourceException;
import com.coditas.multitenantelectricitymanagement.mapper.UserMapper;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityHeadService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createCityHead(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(ExceptionConstants.DUPLICATE_RESOURCE);
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CITY_HEAD);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}
