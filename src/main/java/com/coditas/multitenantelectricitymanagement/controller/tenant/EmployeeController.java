package com.coditas.multitenantelectricitymanagement.controller.tenant;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.bpostate.BPOStateRequest;
import com.coditas.multitenantelectricitymanagement.dto.bpostate.BPOStateResponse;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeRequest;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeResponse;
import com.coditas.multitenantelectricitymanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ApiPath.Employee.SALES)
    public ResponseEntity<ApplicationResponse<EmployeeResponse>> onboardSalesTeamMember(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.onboardSalesTeamMember(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Employee.SALES);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("successfully onboarded sales team member")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('SALES_TEAM')")
    @PostMapping(ApiPath.Employee.OPERATION_HEAD)
    public ResponseEntity<ApplicationResponse<EmployeeResponse>> onboardOperationsHead(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.onboardOperationsHead(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Employee.OPERATION_HEAD);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("successfully onboarded Operations Head")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('OPERATION_HEAD')")
    @PostMapping(ApiPath.Employee.BPO)
    public ResponseEntity<ApplicationResponse<EmployeeResponse>> onboardBPO(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.onboardBPO(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Employee.BPO);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("successfully onboarded BPO")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('OPERATION_HEAD')")
    @PostMapping(ApiPath.Employee.MANAGER1)
    public ResponseEntity<ApplicationResponse<EmployeeResponse>> onboardM1(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.onboardM1(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Employee.MANAGER1);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("successfully onboarded Manager 1")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('OPERATION_HEAD')")
    @PostMapping(ApiPath.Employee.MANAGER2)
    public ResponseEntity<ApplicationResponse<EmployeeResponse>> onboardM2(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.onboardM2(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Employee.MANAGER2);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("successfully onboarded Manager 1")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('OPERATION_HEAD')")
    @PostMapping(ApiPath.Employee.BPOSTATE)
    public ResponseEntity<ApplicationResponse<BPOStateResponse>> assignBPOState(@Valid @RequestBody BPOStateRequest request) {
        BPOStateResponse response = employeeService.assignBPOState(request);
        return ResponseEntity.ok(
                ApplicationResponse.<BPOStateResponse>builder()
                        .success(true)
                        .message("successfully onboarded BPO")
                        .data(response)
                        .build()
        );
    }

    @GetMapping(ApiPath.Employee.SALES+"/{id}")
    public ResponseEntity<ApplicationResponse<EmployeeResponse>> getSalesTeam(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getSalesTeam(id);
        return ResponseEntity.ok(
                ApplicationResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("successfully get sales team member")
                        .data(response)
                        .build()
        );
    }

}
