package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalTechnicianService {

    private final ServiceUtil serviceUtil;

    public UserResponse onboardLocalTechnician(Long cityId, @Valid UserRequest request) {
        return serviceUtil.persistUser(request, Role.LOCAL_TECHNICIAN);
    }
}
