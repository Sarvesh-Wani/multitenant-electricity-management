package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.DistrictHeadService;
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
@RequestMapping(ApiPath.DistrictHead.DISTRICT_HEAD)
@RequiredArgsConstructor
public class DistrictHeadController {

    private final DistrictHeadService districtHeadService;

    @PreAuthorize("hasRole('STATE_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardStateHead(@Valid @RequestBody UserRequest request) {
        UserResponse response = districtHeadService.createDistrictHead(request);

        URI location = URI.create(ApiPath.DistrictHead.DISTRICT_HEAD);

        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("District Head successfully on-boarded!")
                        .data(response)
                        .build()
        );
    }
}
