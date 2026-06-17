package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeRequest;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeResponse;
import com.coditas.multitenantelectricitymanagement.entity.Employee;
import com.coditas.multitenantelectricitymanagement.enums.TenantRole;
import com.coditas.multitenantelectricitymanagement.exception.DuplicateResourceException;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.mapper.EmployeeMapper;
import com.coditas.multitenantelectricitymanagement.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeResponse onboardSalesTeamMember(@Valid EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            log.warn("ObBoarding failed, user with email {} already exist", request.getEmail());
            throw new DuplicateResourceException(ExceptionConstants.DUPLICATE_EMPLOYEE_FOUND);
        }
        Employee employee = employeeMapper.toEntity(request);
        employee.setRole(TenantRole.SALES_TEAM);
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }

    public EmployeeResponse getSalesTeam(Long id) {
        Employee response = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND));
        return employeeMapper.toDTO(response);
    }
}
