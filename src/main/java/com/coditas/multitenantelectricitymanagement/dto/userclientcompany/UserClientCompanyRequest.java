package com.coditas.multitenantelectricitymanagement.dto.userclientcompany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserClientCompanyRequest {

    private Long user;
    private Long clientCompany;
}
