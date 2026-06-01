package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.dto.EmployeeRegisterRequest;
import com.coditas.multitenantelectricitymanagement.dto.EmployeeRegisterResponse;
import com.coditas.multitenantelectricitymanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<EmployeeRegisterResponse> registerEmployee(@RequestBody EmployeeRegisterRequest request,
                                                                     @RequestParam("tenant") String tenant) {
        EmployeeRegisterResponse response = employeeService.registerEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
