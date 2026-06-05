package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.employee.EmployeeRegisterRequest;
import com.coditas.multitenantelectricitymanagement.dto.employee.EmployeeRegisterResponse;
import com.coditas.multitenantelectricitymanagement.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EmployeeMapper {

    Employee toEntity(EmployeeRegisterRequest request);

    EmployeeRegisterResponse toDTO(Employee employee);

    List<EmployeeRegisterResponse> toEmployeeRegisterResponseList(List<Employee> employees);
}
