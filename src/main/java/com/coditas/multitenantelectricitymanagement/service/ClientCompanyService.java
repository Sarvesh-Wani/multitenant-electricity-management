package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.CompanyRequest;
import com.coditas.multitenantelectricitymanagement.dto.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.entity.ClientCompany;
import com.coditas.multitenantelectricitymanagement.enums.CompanyStatus;
import com.coditas.multitenantelectricitymanagement.repository.ClientCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;
    private final TenantService tenantService;


    public CompanyResponse registerCompany(CompanyRequest request) {

        ClientCompany company = new ClientCompany();
        company.setCompanyName(request.getCompanyName());
        company.setTenantId(request.getTenantId());
        company.setStatus(CompanyStatus.ACTIVE);
        ClientCompany savedCompany = clientCompanyRepository.save(company);
        tenantService.createSchema(savedCompany.getTenantId());

        return CompanyResponse.builder()
                .companyName(savedCompany.getCompanyName())
                .tenantId(savedCompany.getTenantId())
                .status(savedCompany.getStatus())
                .build();
    }
}
