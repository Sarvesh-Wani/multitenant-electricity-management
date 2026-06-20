package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityHeadService {

    private final ServiceUtil serviceUtil;

    public UserResponse createCityHead(UserRequest request) {
        return serviceUtil.persistUser(request, Role.CITY_HEAD);
    }

    public UserResponse retrieveCityHead(Long id) {
        return serviceUtil.findById(id, Role.CITY_HEAD);
    }
    public List<UserResponse> retrieveAllCityHead() {
        return serviceUtil.findAll(Role.CITY_HEAD);
    }
}
