package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.PaginationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.SalesTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class SalesTeamController {

    private final SalesTeamService salesTeamService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(ApiPath.SuperAdmin.ONBOARD_SALES_TEAM_MEMBER)
    public ResponseEntity<ApplicationResponse<UserResponse>> onBoardSalesTeamMember(@Valid @RequestBody UserRequest request) {

        UserResponse response = salesTeamService.createSalesTeamMember(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.SalesTeam.SALES_TEAM + ApiPath.SalesTeam.ID.replace("{sid}", String.valueOf(response.getId())));
        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Onboarded a sales team member")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(ApiPath.SalesTeam.SALES_TEAM + ApiPath.SalesTeam.ID)
    public ResponseEntity<ApplicationResponse<UserResponse>> retrieveSalesTeamMember(@PathVariable(name = "sid") Long id) {

        UserResponse response = salesTeamService.retrieveSalesTeamMember(id);
        return ResponseEntity.ok(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a sales team member")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(ApiPath.SalesTeam.SALES_TEAM)
    public ResponseEntity<ApplicationResponse<PaginationResponse<UserResponse>>> retrieveAllSalesTeamMember(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        PaginationResponse<UserResponse> response = salesTeamService.retrieveAllSalesTeamMember(page, size);
        return ResponseEntity.ok(
                ApplicationResponse.<PaginationResponse<UserResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all sales team member")
                        .data(response)
                        .build()
        );
    }
}
