package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.SalesTeamService;
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
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class SalesTeamController {

    private final SalesTeamService salesTeamService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(ApiPath.SuperAdmin.ONBOARD_SALES_TEAM_MEMBER)
    public ResponseEntity<ApplicationResponse<UserResponse>> onBoardSalesTeamMember(@Valid @RequestBody UserRequest request) {

        UserResponse response = salesTeamService.createSalesTeamMember(request);
        URI location = URI.create(ApiPath.BASE_PATH);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Onboarded a sales team member")
                        .data(response)
                        .build()
        );
    }
}
