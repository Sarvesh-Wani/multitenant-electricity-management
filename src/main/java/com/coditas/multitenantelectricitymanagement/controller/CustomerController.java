package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerRequest;
import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerResponse;
import com.coditas.multitenantelectricitymanagement.dto.userclientcompany.UserClientCompanyRequest;
import com.coditas.multitenantelectricitymanagement.dto.userclientcompany.UserClientCompanyResponse;
import com.coditas.multitenantelectricitymanagement.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.Customer.BASE)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('CRM')")
    @PostMapping
    public ResponseEntity<ApplicationResponse<CustomerResponse>> registerCustomer(@PathVariable(name = "area-id") Long areaId, @Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.registerCustomer(areaId, request);
        URI location = URI.create(ApiPath.BASE_PATH);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("A new Customer is successfully registered")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('CRM')")
    @PostMapping(ApiPath.Customer.ASSIGNMENT)
    public ResponseEntity<ApplicationResponse<UserClientCompanyResponse>> assignServiceProvider(@Valid @RequestBody UserClientCompanyRequest request) {
        UserClientCompanyResponse response = customerService.assignServiceProvider(request);
        return ResponseEntity.ok().body(
                ApplicationResponse.<UserClientCompanyResponse>builder()
                        .success(true)
                        .message("A service provider is successfully assigned!")
                        .data(response)
                        .build()
        );
    }

}
