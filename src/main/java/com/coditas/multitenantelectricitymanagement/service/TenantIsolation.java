package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.cutomerquery.CustomerQueryResponse;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeRequest;
import com.coditas.multitenantelectricitymanagement.entity.Employee;
import com.coditas.multitenantelectricitymanagement.entity.tenant.CustomerQuery;
import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import com.coditas.multitenantelectricitymanagement.enums.TenantRole;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.mapper.CustomerQueryMapper;
import com.coditas.multitenantelectricitymanagement.mapper.EmployeeMapper;
import com.coditas.multitenantelectricitymanagement.repository.CustomerQueryRepository;
import com.coditas.multitenantelectricitymanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TenantIsolation {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerQueryRepository customerQueryRepository;
    private final CustomerQueryMapper customerQueryMapper;

    public void registerEmployee(EmployeeRequest request) {
        Employee entity = employeeMapper.toEntity(request);
        entity.setRole(TenantRole.ADMIN);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        employeeRepository.save(entity);
    }

    public CustomerQueryResponse updateStatus(Long id, QueryStatus status) {
        CustomerQuery query = customerQueryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.CUSTOMER_QUERY_NOT_FOUND));
        query.setStatus(status);
        CustomerQuery savedQuery = customerQueryRepository.save(query);
        return customerQueryMapper.toDTO(savedQuery);
    }

}
