package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.city.CityRequest;
import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(ApiPath.City.BASE)
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PreAuthorize("hasAnyRole('STATE_HEAD', 'DISTRICT_HEAD', 'MANAGER')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<CityResponse>> createCity(@Valid @RequestBody CityRequest request) {
        log.info("Received request to create city with name: {}", request.getName());
        CityResponse response = cityService.createCity(request);
        URI location = URI.create(ApiPath.City.BASE);
        log.info("Successfully created city with Id {}", response.getId());
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
    public ResponseEntity<ApplicationResponse<CityResponse>> assignCityHead(@PathVariable(name = "id") Long cityId,
                                                                            @Valid @RequestBody HeadAssignmentRequest request) {
        log.info("Received request to assign city head to city: {}", cityId);
        CityResponse response = cityService.assignCityHead(cityId, request);
        log.info("Successfully assigned city head to city ");
        return ResponseEntity.ok(
                ApplicationResponse.<CityResponse>builder()
                        .success(true)
                        .message("City head is assigned to a District")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('STATE_HEAD', 'DISTRICT_HEAD', 'MANAGER')")
    @GetMapping(ApiPath.City.ID)
    public ResponseEntity<ApplicationResponse<CityResponse>> retrieveCityById(@PathVariable Long id) {

        CityResponse response = cityService.retrieveCityById(id);
        return ResponseEntity.ok(
                ApplicationResponse.<CityResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a city")
                        .data(response)
                        .build()
        );
    }
    @PreAuthorize("hasAnyRole('STATE_HEAD', 'DISTRICT_HEAD', 'MANAGER')")
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<CityResponse>>> retrieveAllCities() {

        List<CityResponse> response = cityService.retrieveAllCities();
        return ResponseEntity.ok(
                ApplicationResponse.<List<CityResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all cities")
                        .data(response)
                        .build()
        );
    }


}
