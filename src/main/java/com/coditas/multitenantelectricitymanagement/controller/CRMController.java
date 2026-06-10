package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.CRMService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(ApiPath.CRM.BASE)
@RequiredArgsConstructor
public class CRMController {

    private final CRMService crmService;

    @PreAuthorize("hasRole('CITY_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardCRM(@Valid @RequestBody UserRequest request) {
        log.info("Received request to onboard CRM with email: {}", request.getEmail());
        UserResponse response = crmService.onboardCRM(request);
        URI location = URI.create(ApiPath.CRM.BASE);
        log.info("Successfully onboarded CRM. Assigned ID: {}", response.getId());
        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully onboarded CRM!")
                        .data(response)
                        .build()
        );
    }
}
