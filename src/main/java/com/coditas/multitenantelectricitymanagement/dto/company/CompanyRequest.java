package com.coditas.multitenantelectricitymanagement.dto.company;

import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {

    @NotBlank
    private String companyName;

    @NotBlank
    private String tenantId;

    private EmployeeRequest employee;

}
