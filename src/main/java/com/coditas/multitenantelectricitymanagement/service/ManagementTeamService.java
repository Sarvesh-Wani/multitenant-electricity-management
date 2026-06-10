package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagementTeamService {

    private final ServiceUtil serviceUtil;

    public UserResponse createManagementTeamMember(UserRequest request) {
        return serviceUtil.persistUser(request, Role.MANAGER);
    }
}
