package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeRequest;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeResponse;
import com.coditas.multitenantelectricitymanagement.entity.Employee;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    Employee toEntity(@Valid EmployeeRequest request);

    EmployeeResponse toDTO(Employee savedEmployee);
}
