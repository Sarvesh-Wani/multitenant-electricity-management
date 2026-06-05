package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.mapper.UserMapper;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createManagementTeamMember(UserRequest request) {

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MANAGER);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserResponse createSalesTeamMember(UserRequest request) {

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.SALES_TEAM_MEMBER);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCENOTFOUND)
        );
        return userMapper.toDTO(user);
    }
}
