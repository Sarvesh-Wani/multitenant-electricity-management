package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPath.SuperAdmin.ONBOARD_MANAGEMENT_TEAM_MEMBER)
    public ResponseEntity<ApplicationResponse<UserResponse>> onBoardManagementTeamMember(@Valid @RequestBody UserRequest request) {

        UserResponse response = userService.createManagementTeamMember(request);

        URI location = URI.create(ApiPath.BASE_PATH);

        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Onboarded a management team member")
                        .data(response)
                        .build()
        );
    }

    @PostMapping(ApiPath.SuperAdmin.ONBOARD_SALES_TEAM_MEMBER)
    public ResponseEntity<ApplicationResponse<UserResponse>> onBoardSalesTeamMember(@Valid @RequestBody UserRequest request) {

        UserResponse response = userService.createSalesTeamMember(request);
        URI location = URI.create(ApiPath.BASE_PATH);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Onboarded a sales team member")
                        .data(response)
                        .build()
        );
    }

    @GetMapping(ApiPath.SalesTeam.BASE+"/{id}")
    public ResponseEntity<ApplicationResponse<UserResponse>> getUserById(@PathVariable Long id){
        UserResponse response = userService.getUserById(id);

        return ResponseEntity.ok().body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully retrieved a user")
                        .data(response)
                        .build()
        );
    }




}
