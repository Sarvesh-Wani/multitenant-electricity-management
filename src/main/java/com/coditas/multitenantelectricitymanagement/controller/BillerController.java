package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.BillerService;
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
@RequestMapping(ApiPath.Biller.BASE)
@RequiredArgsConstructor
public class BillerController {

    private final BillerService billerService;

    @PreAuthorize("hasRole('CITY_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardBiller(@Valid @RequestBody UserRequest request) {
        log.info("Received request to onboard bill serviceman with email: {}", request.getEmail());
        UserResponse response = billerService.onboardBiller(request);
        URI location = URI.create(ApiPath.Biller.BASE+ApiPath.Biller.ID.replace("{id}", String.valueOf(response.getId())));
        log.info("Successfully onboarded biller. Assigned ID: {}", response.getId());
        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully onboarded Bill Serviceman!")
                        .data(response)
                        .build()
        );
    }
}
