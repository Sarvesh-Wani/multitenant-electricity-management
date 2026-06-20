package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRMService {

    private final ServiceUtil serviceUtil;

    public UserResponse onboardCRM(UserRequest request) {
        return serviceUtil.persistUser(request, Role.CRM);
    }

    public UserResponse retrieveCRM(Long id) {
        return serviceUtil.findById(id, Role.CRM);
    }

    public List<UserResponse> retrieveAllCRM() {
        return serviceUtil.findAll(Role.CRM);
    }
}
