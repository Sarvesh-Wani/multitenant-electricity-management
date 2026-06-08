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

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class SalesTaskController {

    private final SalesTaskService taskService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(ApiPath.Task.SALES_TASK)
    public ResponseEntity<ApplicationResponse<SalesTaskResponse>> createSalesTask(@Valid @RequestBody SalesTaskRequest request) {
        SalesTaskResponse response = taskService.createTask(request);
        URI location = URI.create(ApiPath.BASE_PATH);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<SalesTaskResponse>builder()
                        .success(true)
                        .message("A Task Successfully created!")
                        .data(response)
                        .build()
        );
    }


}
