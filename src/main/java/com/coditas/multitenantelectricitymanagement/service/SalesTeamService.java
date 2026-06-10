package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesTeamService {

    private final ServiceUtil serviceUtil;

    public UserResponse createSalesTeamMember(UserRequest request) {
        return serviceUtil.persistUser(request, Role.SALES_TEAM_MEMBER);
    }
}
