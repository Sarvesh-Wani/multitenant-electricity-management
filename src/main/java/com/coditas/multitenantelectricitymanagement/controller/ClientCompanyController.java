package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.dto.CompanyRequest;
import com.coditas.multitenantelectricitymanagement.dto.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.service.ClientCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ClientCompanyController {

    private final ClientCompanyService clientCompanyService;

    @PostMapping("/registerCompany")
    public ResponseEntity<CompanyResponse> registerCompany(@RequestBody CompanyRequest request) {
        CompanyResponse response = clientCompanyService.registerCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
