package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.ManagementTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class ManagementTeamController {

    private final ManagementTeamService managementTeamService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping(ApiPath.SuperAdmin.ONBOARD_MANAGEMENT_TEAM_MEMBER)
    public ResponseEntity<ApplicationResponse<UserResponse>> onBoardManagementTeamMember(@Valid @RequestBody UserRequest request) {

        UserResponse response = managementTeamService.createManagementTeamMember(request);

        URI location = URI.create(ApiPath.BASE_PATH);

        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Onboarded a management team member")
                        .data(response)
                        .build()
        );
    }

}
