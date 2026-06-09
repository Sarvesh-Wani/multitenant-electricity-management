package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.CityHeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.CityHead.CITY_HEAD)
@RequiredArgsConstructor
public class CityHeadController {

    private final CityHeadService cityHeadService;

    @PreAuthorize("hasRole('DISTRICT_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardCityHead(@Valid @RequestBody UserRequest request) {
        UserResponse response = cityHeadService.createCityHead(request);

        URI location = URI.create(ApiPath.BASE_PATH);

        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("City Head successfully on-boarded!")
                        .data(response)
                        .build()
        );
    }
}
