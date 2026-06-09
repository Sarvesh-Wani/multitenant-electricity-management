package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.city.CityRequest;
import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.City.BASE)
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PreAuthorize("hasAnyRole('STATE_HEAD', 'DISTRICT_HEAD', 'MANAGER')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<CityResponse>> createCity(@Valid @RequestBody CityRequest request) {
        CityResponse response = cityService.createCity(request);
        URI location = URI.create(ApiPath.City.BASE);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<CityResponse>builder()
                        .success(true)
                        .message("Successfully created new city")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STATE_HEAD', 'DISTRICT_HEAD')")
    @PutMapping(ApiPath.City.ID)
    public ResponseEntity<ApplicationResponse<CityResponse>> assignStateHead(@PathVariable(name = "id") Long cityId,
                                                                             @Valid @RequestBody HeadAssignmentRequest request) {
        CityResponse response = cityService.assignCityHead(cityId, request);

        return ResponseEntity.ok(
                ApplicationResponse.<CityResponse>builder()
                        .success(true)
                        .message("City head is assigned to a District")
                        .data(response)
                        .build()
        );
    }


}
