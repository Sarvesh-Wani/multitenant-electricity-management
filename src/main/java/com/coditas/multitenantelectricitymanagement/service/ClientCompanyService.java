package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.company.CompanyRequest;
import com.coditas.multitenantelectricitymanagement.dto.company.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.entity.ClientCompany;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.CompanyStatus;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.mapper.ClientCompanyMapper;
import com.coditas.multitenantelectricitymanagement.repository.ClientCompanyRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import com.coditas.multitenantelectricitymanagement.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;
    private final TenantService tenantService;
    private final UserRepository userRepository;
    private final ClientCompanyMapper companyMapper;
    private final TenantIsolation tenantIsolation;

    @Transactional
    public CompanyResponse registerCompany(Long sid, CompanyRequest request) {

        User salesTeam = userRepository.findById(sid).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        ClientCompany company = new ClientCompany();

        company.setCompanyName(request.getCompanyName());
        company.setTenantId(request.getTenantId());
        company.setStatus(CompanyStatus.ACTIVE);
        company.setSalesTeamMember(salesTeam);

        ClientCompany savedCompany = clientCompanyRepository.save(company);
        tenantService.createSchema(savedCompany.getTenantId());

        TenantContext.setTenant(savedCompany.getTenantId());
        tenantIsolation.registerEmployee(request.getEmployee());

        return companyMapper.toDTO(savedCompany);
    }
}
