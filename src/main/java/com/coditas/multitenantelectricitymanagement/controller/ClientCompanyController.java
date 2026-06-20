package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
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

import java.util.List;

@RestController
@RequestMapping(ApiPath.SalesTeam.BASE)
@RequiredArgsConstructor
public class ClientCompanyController {

    private final ClientCompanyService clientCompanyService;

    @PreAuthorize("hasRole('SALES_TEAM_MEMBER')")
    @PostMapping(ApiPath.SalesTeam.ID + ApiPath.SalesTeam.CLIENT_COMPANY)
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

    @PreAuthorize("hasRole('SALES_TEAM_MEMBER')")
    @GetMapping(ApiPath.SalesTeam.CLIENT_COMPANY + ApiPath.ID)
    public ResponseEntity<ApplicationResponse<CompanyResponse>> retrieveClientById(@PathVariable Long id) {

        CompanyResponse response = clientCompanyService.retrieveClientById(id);
        return ResponseEntity.ok(
                ApplicationResponse.<CompanyResponse>builder()
                        .success(true)
                        .message("Successfully Retrieved a clients")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('SALES_TEAM_MEMBER')")
    @GetMapping(ApiPath.SalesTeam.CLIENT_COMPANY)
    public ResponseEntity<ApplicationResponse<List<CompanyResponse>>> retrieveAllClients() {

        List<CompanyResponse> response = clientCompanyService.retrieveAllClients();
        return ResponseEntity.ok(
                ApplicationResponse.<List<CompanyResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all Client")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('SALES_TEAM_MEMBER')")
    @GetMapping(ApiPath.SalesTeam.ID + ApiPath.SalesTeam.CLIENT_COMPANY)
    public ResponseEntity<ApplicationResponse<List<CompanyResponse>>> retrieveAllClientsBySalesId(@PathVariable Long sid) {

        List<CompanyResponse> response = clientCompanyService.retrieveAllClientsBySalesId(sid);
        return ResponseEntity.ok(
                ApplicationResponse.<List<CompanyResponse>>builder()
                        .success(true)
                        .message("Successfully Retrieved all clients onboarded by sales team with provided id")
                        .data(response)
                        .build()
        );
    }

}
