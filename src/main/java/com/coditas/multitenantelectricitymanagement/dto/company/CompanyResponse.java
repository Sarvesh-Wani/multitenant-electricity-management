package com.coditas.multitenantelectricitymanagement.dto.company;

import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.CompanyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class CompanyResponse {

    private String companyName;

    private String tenantId;

    private String email;

    private CompanyStatus status;

    private UserResponse salesTeamMember;

    private Instant createdAt;
}
