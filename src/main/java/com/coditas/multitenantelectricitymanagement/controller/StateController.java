package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.dto.state.StateRequest;
import com.coditas.multitenantelectricitymanagement.dto.state.StateResponse;
import com.coditas.multitenantelectricitymanagement.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiPath.State.BASE)
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<StateResponse>> createState(@Valid @RequestBody StateRequest
                                                                                  request) {
        StateResponse response = stateService.createState(request);
        URI location = URI.create(ApiPath.State.BASE+ApiPath.State.ID.replace("{id}", String.valueOf(response.getId())));
        return ResponseEntity.created(location).body(
                ApplicationResponse.<StateResponse>builder()
                        .success(true)
                        .message("A new state successfully added!")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    @PutMapping(ApiPath.State.ID)
    public ResponseEntity<ApplicationResponse<StateResponse>> assignStateHead(@PathVariable(name = "id") Long stateId,
                                                                              @Valid @RequestBody HeadAssignmentRequest request) {
        StateResponse response = stateService.assignStateHead(stateId, request);

        return ResponseEntity.ok(
                ApplicationResponse.<StateResponse>builder()
                        .success(true)
                        .message("State head is assigned to a state")
                        .data(response)
                        .build()
        );
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping(ApiPath.State.ID)
    public ResponseEntity<ApplicationResponse<StateResponse>> retrieveStateById(@PathVariable Long id) {

        StateResponse response = stateService.retrieveStateById(id);
        return ResponseEntity.ok(
                ApplicationResponse.<StateResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a state")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<StateResponse>>> retrieveAllStates() {

        List<StateResponse> response = stateService.retrieveAllStates();
        return ResponseEntity.ok(
                ApplicationResponse.<List<StateResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all states")
                        .data(response)
                        .build()
        );
    }
}
