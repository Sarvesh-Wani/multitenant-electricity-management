package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.PaginationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesTeamService {

    private final ServiceUtil serviceUtil;

    public UserResponse createSalesTeamMember(UserRequest request) {
        return serviceUtil.persistUser(request, Role.SALES_TEAM_MEMBER);
    }

    public UserResponse retrieveSalesTeamMember(Long id) {
        return serviceUtil.findById(id, Role.SALES_TEAM_MEMBER);
    }

    public PaginationResponse<UserResponse> retrieveAllSalesTeamMember(int page, int size) {
        return serviceUtil.findAll(page, size, Role.SALES_TEAM_MEMBER);
    }

}
