package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.DataRequest;
import com.coditas.multitenantelectricitymanagement.dto.cutomerquery.CustomerQueryRequest;
import com.coditas.multitenantelectricitymanagement.dto.cutomerquery.CustomerQueryResponse;
import com.coditas.multitenantelectricitymanagement.entity.Customer;
import com.coditas.multitenantelectricitymanagement.entity.tenant.CustomerQuery;
import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.CustomerQueryMapper;
import com.coditas.multitenantelectricitymanagement.repository.CustomerQueryRepository;
import com.coditas.multitenantelectricitymanagement.repository.CustomerRepository;
import com.coditas.multitenantelectricitymanagement.tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerQueryService {
    private final CustomerQueryRepository customerQueryRepository;
    private final CustomerRepository customerRepository;
    private final CustomerQueryMapper customerQueryMapper;
    private final TenantIsolation tenantIsolation;

    public CustomerQueryResponse raiseCustomerQuery(Long id, @Valid CustomerQueryRequest request) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.CUSTOMER_NOT_FOUND));
        if (!customer.getUser().getRole().equals(Role.CUSTOMER)) {
            throw new RoleMisMatchException(ExceptionConstants.CUSTOMER_ROLE_MISMATCH);
        }

        CustomerQuery query = new CustomerQuery();
        query.setQuery(request.getQuery());
        query.setStatus(QueryStatus.PENDING);
        query.setCustomer(customer);
        CustomerQuery savedQuery = customerQueryRepository.save(query);
        return customerQueryMapper.toDTO(savedQuery);
    }


    public CustomerQueryResponse updateStatustoResolve(Long id, DataRequest request) {
        TenantContext.setTenant(request.getTenantId());
        return tenantIsolation.updateStatus(id, QueryStatus.RESOLVED);
    }

    public CustomerQueryResponse updateStatusToM1(Long id, DataRequest request) {
        TenantContext.setTenant(request.getTenantId());
        return tenantIsolation.updateStatus(id, QueryStatus.ESCALATED_TO_M1);
    }

    public CustomerQueryResponse updateStatusToM2(Long id, DataRequest request) {
        TenantContext.setTenant(request.getTenantId());
        return tenantIsolation.updateStatus(id, QueryStatus.ESCALATED_TO_M2);
    }


}
