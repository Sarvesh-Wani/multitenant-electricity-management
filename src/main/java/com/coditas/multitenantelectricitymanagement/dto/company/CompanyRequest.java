package com.coditas.multitenantelectricitymanagement.dto.company;

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

    @NotBlank
    private String email;

}
