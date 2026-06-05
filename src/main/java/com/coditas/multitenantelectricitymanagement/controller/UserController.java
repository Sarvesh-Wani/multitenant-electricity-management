package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPath.SuperAdmin.ONBOARD_MANAGEMENT_TEAM_MEMBER)
    public ResponseEntity<ApplicationResponse<UserResponse>> onBoardManagementTeamMember(@Valid @RequestBody UserRequest request) {

        UserResponse response = userService.createUser(request);

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
