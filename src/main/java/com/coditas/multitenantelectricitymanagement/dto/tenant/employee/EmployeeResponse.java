package com.coditas.multitenantelectricitymanagement.dto.tenant.employee;

import com.coditas.multitenantelectricitymanagement.enums.TenantRole;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String password;
    private TenantRole role;
    private Instant createdAt;
}
