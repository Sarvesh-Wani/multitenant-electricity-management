package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.salestask.SalesTaskRequest;
import com.coditas.multitenantelectricitymanagement.dto.salestask.SalesTaskResponse;
import com.coditas.multitenantelectricitymanagement.service.SalesTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class SalesTaskController {

    private final SalesTaskService taskService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(ApiPath.Task.SALES_TASK)
    public ResponseEntity<ApplicationResponse<SalesTaskResponse>> createSalesTask(@Valid @RequestBody SalesTaskRequest request) {
        SalesTaskResponse response = taskService.createTask(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Task.SALES_TASK + ApiPath.SalesTeam.ID.replace("{sid}", response.getId()));
        return ResponseEntity.created(location).body(
                ApplicationResponse.<SalesTaskResponse>builder()
                        .success(true)
                        .message("A Task Successfully created!")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(ApiPath.Task.SALES_TASK)
    public ResponseEntity<ApplicationResponse<List<SalesTaskResponse>>> findAllSalesTasks() {
        List<SalesTaskResponse> response = taskService.findAllSalesTasks();
        return ResponseEntity.ok(
                ApplicationResponse.<List<SalesTaskResponse>>builder()
                        .success(true)
                        .message("Retrieved all the sales tasks")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('SALES_TEAM_MEMBER')")
    @GetMapping(ApiPath.SalesTeam.SALES_TEAM + ApiPath.SalesTeam.ID + ApiPath.Task.SALES_TASK)
    public ResponseEntity<ApplicationResponse<List<SalesTaskResponse>>> findSalesTasksAssignedToSalesTeam(@PathVariable(name = "sid") Long id) {
        List<SalesTaskResponse> response = taskService.findSalesTasksAssignedToSalesTeam(id);
        return ResponseEntity.ok(
                ApplicationResponse.<List<SalesTaskResponse>>builder()
                        .success(true)
                        .message("Retrieved all the sales tasks assigned to sales team member with provided Id")
                        .data(response)
                        .build()
        );
    }
}
