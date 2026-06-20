package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.service.DistrictHeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiPath.DistrictHead.DISTRICT_HEAD)
@RequiredArgsConstructor
public class DistrictHeadController {

    private final DistrictHeadService districtHeadService;

    @PreAuthorize("hasRole('STATE_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> onboardDistrictHead(@Valid @RequestBody UserRequest request) {
        UserResponse response = districtHeadService.createDistrictHead(request);

        URI location = URI.create(ApiPath.DistrictHead.DISTRICT_HEAD + ApiPath.ID.replace("{id}", String.valueOf(response.getId())));

        return ResponseEntity.created(location).body(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("District Head successfully on-boarded!")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('STATE_HEAD')")
    @GetMapping(ApiPath.ID)
    public ResponseEntity<ApplicationResponse<UserResponse>> retrieveDistrictHead(@PathVariable Long id) {

        UserResponse response = districtHeadService.retrieveDistrictHead(id);
        return ResponseEntity.ok(
                ApplicationResponse.<UserResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a District Head")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('STATE_HEAD')")
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<UserResponse>>> retrieveAllDistrictHead() {

        List<UserResponse> response = districtHeadService.retrieveAllDistrictHead();
        return ResponseEntity.ok(
                ApplicationResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all District head")
                        .data(response)
                        .build()
        );
    }
}
