package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.StateHeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPath.StateHead.STATE_HEAD)
public class StateHeadController {

    private final StateHeadService stateHeadService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardStateHead(@Valid @RequestBody UserRequest request) {
        UserResponse response = stateHeadService.createStateHead(request);

        URI location = URI.create(ApiPath.StateHead.STATE_HEAD);

        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("State Head successfully on-boarded!")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(ApiPath.SalesTeam.ID)
    public ResponseEntity<ApplicationResponse<UserResponse>> retrieveStateHead(@PathVariable(name = "sid") Long id) {

        UserResponse response = stateHeadService.retrieveStateHead(id);
        return ResponseEntity.ok(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a State Head")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<UserResponse>>> retrieveAllStateHead() {

        List<UserResponse> response = stateHeadService.retrieveAllStateHead();
        return ResponseEntity.ok(
                ApplicationResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all state head")
                        .data(response)
                        .build()
        );
    }
}
