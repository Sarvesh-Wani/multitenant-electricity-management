package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.company.CompanyRequest;
import com.coditas.multitenantelectricitymanagement.dto.company.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.service.ClientCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientCompanyController {

    private final ClientCompanyService clientCompanyService;

    @PreAuthorize("hasRole('SALES_TEAM_MEMBER')")
    @PostMapping("sales-team/{sid}/client-companies")
    public ResponseEntity<ApplicationResponse<CompanyResponse>> registerCompany(@PathVariable Long sid, @Valid @RequestBody CompanyRequest request) {
        CompanyResponse response = clientCompanyService.registerCompany(sid, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApplicationResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Successfully registered new client company on the platform")
                        .data(response)
                        .build()
        );

    }

}
