package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.LocalTechnicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(ApiPath.LocalTechnician.BASE)
@RequiredArgsConstructor
public class LocalTechnicianController {

    private final LocalTechnicianService localTechnicianService;

    @PreAuthorize("hasRole('CITY_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardLocalTechnician(@PathVariable Long cityId, @Valid @RequestBody UserRequest request) {
        log.info("Processing request to onboard a local technician with email id {} ", request.getEmail());
        UserResponse response = localTechnicianService.onboardLocalTechnician(cityId, request);
        URI location = URI.create(ApiPath.LocalTechnician.BASE.replace("{cityId}", String.valueOf(cityId)));
        log.info("Successfully onboarded local technician with saved id {} ", response.getId());
        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully onboarded new local technician!")
                        .data(response)
                        .build()
        );
    }
}
