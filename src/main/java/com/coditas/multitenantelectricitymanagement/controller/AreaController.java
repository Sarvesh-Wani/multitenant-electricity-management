package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.area.AreaRequest;
import com.coditas.multitenantelectricitymanagement.dto.area.AreaResponse;
import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.service.AreaService;
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
@RequestMapping(ApiPath.Area.BASE)
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @PreAuthorize("hasRole('CITY_HEAD')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<AreaResponse>> registerArea(@PathVariable(name = "city-id") Long cityId, @Valid @RequestBody AreaRequest request) {
        log.info("Processing the request for area registration with name {} ", request.getName());
        AreaResponse response = areaService.registerArea(cityId, request);
        URI location = URI.create(ApiPath.Area.BASE.replace("{city-id}", cityId.toString()));
        log.info("Successfully registered area with registered ID {} ", response.getId());
        return ResponseEntity.created(location).body(
                ApplicationResponse.<AreaResponse>builder()
                        .success(true)
                        .message("A new area is successfully registered")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('CITY_HEAD')")
    @PostMapping(ApiPath.Area.ID + ApiPath.Area.TECHNICIAN)
    public ResponseEntity<ApplicationResponse<AreaResponse>> assignTechnician(@PathVariable(name = "city-id") Long cityId,
                                                                              @PathVariable(name = "area-id") Long areaId,
                                                                              @Valid @RequestBody HeadAssignmentRequest request) {

        log.info("Processing the request for technician assignement with id {} ", request.getId());
        AreaResponse response = areaService.assignTechnicians(cityId, areaId, request);
        log.info("Successfully assigned technician to area with ID {} ", areaId);
        return ResponseEntity.ok().body(
                ApplicationResponse.<AreaResponse>builder()
                        .success(true)
                        .message("A new technicians is assigned to area")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('CITY_HEAD')")
    @PostMapping(ApiPath.Area.ID + ApiPath.Area.BILLER)
    public ResponseEntity<ApplicationResponse<AreaResponse>> assignBiller(@PathVariable(name = "city-id") Long cityId,
                                                                          @PathVariable(name = "area-id") Long areaId,
                                                                          @Valid @RequestBody HeadAssignmentRequest request) {

        log.info("Processing the request for biller assignement with id {} ", request.getId());
        AreaResponse response = areaService.assignBiller(cityId, areaId, request);
        log.info("Successfully assigned biller to area with ID {} ", areaId);

        return ResponseEntity.ok().body(
                ApplicationResponse.<AreaResponse>builder()
                        .success(true)
                        .message("A new biller is assigned to area")
                        .data(response)
                        .build()
        );
    }


    @PreAuthorize("hasAnyRole('STATE_HEAD','CITY_HEAD', 'DISTRICT_HEAD', 'MANAGER')")
    @GetMapping
    public ResponseEntity<ApplicationResponse<List<AreaResponse>>> retrieveAllCities(@PathVariable(name = "city-id") Long id) {

        List<AreaResponse> response = areaService.retrieveAllAreas(id);
        return ResponseEntity.ok(
                ApplicationResponse.<List<AreaResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved areas")
                        .data(response)
                        .build()
        );
    }
}
