package com.coditas.multitenantelectricitymanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRegisterRequest {

    private String fullName;
    private String state;
    private String district;
    private String city;

    private UserRequest user;


}
