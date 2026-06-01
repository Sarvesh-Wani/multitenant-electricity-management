package com.coditas.multitenantelectricitymanagement.dto;

import com.coditas.multitenantelectricitymanagement.enums.CompanyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyResponse {

    private Long id;
    private String companyName;

    private String tenantId;

    private CompanyStatus status;
}
