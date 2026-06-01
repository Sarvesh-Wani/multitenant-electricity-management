package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.EmployeeRegisterRequest;
import com.coditas.multitenantelectricitymanagement.dto.EmployeeRegisterResponse;
import com.coditas.multitenantelectricitymanagement.entity.AppUser;
import com.coditas.multitenantelectricitymanagement.entity.Employee;
import com.coditas.multitenantelectricitymanagement.mapper.AppUserMapper;
import com.coditas.multitenantelectricitymanagement.mapper.EmployeeMapper;
import com.coditas.multitenantelectricitymanagement.repository.AppUserRepository;
import com.coditas.multitenantelectricitymanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;

    @Transactional
    public EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request) {

        Employee employee = employeeMapper.toEntity(request);
        AppUser user = appUserMapper.toEntity(request.getUser());

        AppUser savedUser = appUserRepository.save(user);
        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toDTO(savedEmployee);

    }

}
