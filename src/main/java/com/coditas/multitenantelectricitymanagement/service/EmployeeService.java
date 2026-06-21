package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.bpostate.BPOStateRequest;
import com.coditas.multitenantelectricitymanagement.dto.bpostate.BPOStateResponse;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeRequest;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeResponse;
import com.coditas.multitenantelectricitymanagement.entity.Employee;
import com.coditas.multitenantelectricitymanagement.entity.State;
import com.coditas.multitenantelectricitymanagement.entity.tenant.BPOState;
import com.coditas.multitenantelectricitymanagement.entity.tenant.BPOStateID;
import com.coditas.multitenantelectricitymanagement.enums.TenantRole;
import com.coditas.multitenantelectricitymanagement.exception.DuplicateResourceException;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.BPOStateMapper;
import com.coditas.multitenantelectricitymanagement.mapper.EmployeeMapper;
import com.coditas.multitenantelectricitymanagement.repository.BPOStateRepository;
import com.coditas.multitenantelectricitymanagement.repository.EmployeeRepository;
import com.coditas.multitenantelectricitymanagement.repository.StateRepository;
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
    private final StateRepository stateRepostiory;
    private final BPOStateRepository bpoStateRepository;
    private final BPOStateMapper bpoStateMapper;

    public EmployeeResponse onboardSalesTeamMember(EmployeeRequest request) {
        return persistEmployee(request, TenantRole.SALES_TEAM);
    }

    public EmployeeResponse persistEmployee(EmployeeRequest request, TenantRole role) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            log.warn("ObBoarding failed, user with email {} already exist", request.getEmail());
            throw new DuplicateResourceException(ExceptionConstants.DUPLICATE_EMPLOYEE_FOUND);
        }
        Employee employee = employeeMapper.toEntity(request);
        employee.setRole(role);
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }

    public EmployeeResponse getSalesTeam(Long id) {
        Employee response = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND));
        return employeeMapper.toDTO(response);
    }

    public EmployeeResponse onboardOperationsHead(EmployeeRequest request) {
        return persistEmployee(request, TenantRole.OPERATION_HEAD);
    }

    public EmployeeResponse onboardBPO(EmployeeRequest request) {
        return persistEmployee(request, TenantRole.BPO);
    }

    public BPOStateResponse assignBPOState(BPOStateRequest request) {
        BPOState bpoState = new BPOState();
        Employee bpo = employeeRepository.findById(request.getBpo()).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.BPO_NOT_FOUND));
        if (!bpo.getRole().equals(TenantRole.BPO)) {
            throw new RoleMisMatchException(ExceptionConstants.BPO_ROLE_MISMATCH);
        }
        State state = stateRepostiory.findById(request.getState()).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.STATE_NOT_FOUND));
        bpoState.setBpo(bpo);
        bpoState.setState(state);
        bpoState.setId(new BPOStateID());
        BPOState savedBPOState = bpoStateRepository.save(bpoState);
        return bpoStateMapper.toDTO(savedBPOState);
    }

    public EmployeeResponse onboardM1(@Valid EmployeeRequest request) {
        return persistEmployee(request, TenantRole.MANAGER1);
    }

    public EmployeeResponse onboardM2(@Valid EmployeeRequest request) {
        return persistEmployee(request, TenantRole.MANAGER2);
    }
}
