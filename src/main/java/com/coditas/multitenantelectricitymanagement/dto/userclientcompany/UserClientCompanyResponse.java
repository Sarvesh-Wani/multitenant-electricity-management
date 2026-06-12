package com.coditas.multitenantelectricitymanagement.dto.userclientcompany;

import com.coditas.multitenantelectricitymanagement.dto.company.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserClientCompanyResponse {

    private UserResponse user;
    private CompanyResponse clientCompany;
}
