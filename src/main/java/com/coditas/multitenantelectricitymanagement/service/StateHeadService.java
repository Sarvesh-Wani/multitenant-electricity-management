package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateHeadService {

    private final ServiceUtil serviceUtil;

    public UserResponse createStateHead(UserRequest request) {
        return serviceUtil.persistUser(request, Role.STATE_HEAD);
    }
}
