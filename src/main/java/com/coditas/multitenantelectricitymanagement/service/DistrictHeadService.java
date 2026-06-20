package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictHeadService {

    private final ServiceUtil serviceUtil;

    public UserResponse createDistrictHead(UserRequest request) {
        return serviceUtil.persistUser(request, Role.DISTRICT_HEAD);
    }

    public UserResponse retrieveDistrictHead(Long id) {
        return serviceUtil.findById(id, Role.DISTRICT_HEAD);
    }

    public List<UserResponse> retrieveAllDistrictHead() {
        return serviceUtil.findAll(Role.DISTRICT_HEAD);
    }
}
