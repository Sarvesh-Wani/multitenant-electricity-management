package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.district.DistrictRequest;
import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.service.DistrictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiPath.District.BASE)
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PreAuthorize("hasAnyRole('STATE_HEAD', 'MANAGER')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<DistrictResponse>> createDistrict(@Valid @RequestBody DistrictRequest request) {
        DistrictResponse response = districtService.createDistrict(request);
        URI location = URI.create(ApiPath.District.BASE);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<DistrictResponse>builder()
                        .success(true)
                        .message("A new District successfully added!")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STATE_HEAD')")
    @PutMapping(ApiPath.District.ID)
    public ResponseEntity<ApplicationResponse<DistrictResponse>> assignStateHead(@PathVariable(name = "id") Long districtId,
                                                                              @Valid @RequestBody HeadAssignmentRequest request) {
        DistrictResponse response = districtService.assignDistrictHead(districtId, request);

        return ResponseEntity.ok(
                ApplicationResponse.<DistrictResponse>builder()
                        .success(true)
                        .message("District head is assigned to a state")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('STATE_HEAD', 'MANAGER')")
    @GetMapping(ApiPath.District.ID)
    public ResponseEntity<ApplicationResponse<DistrictResponse>> retrieveDistrictById(@PathVariable Long id) {

        DistrictResponse response = districtService.retrieveDistrictById(id);
        return ResponseEntity.ok(
                ApplicationResponse.<DistrictResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a district")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('STATE_HEAD', 'MANAGER')")
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<DistrictResponse>>> retrieveAllDistricts() {

        List<DistrictResponse> response = districtService.retrieveAllDistricts();
        return ResponseEntity.ok(
                ApplicationResponse.<List<DistrictResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all districts")
                        .data(response)
                        .build()
        );
    }
}
