package com.coditas.multitenantelectricitymanagement.dto.customer;

import com.coditas.multitenantelectricitymanagement.dto.area.AreaResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {

    private UserResponse user;
    private AreaResponse area;
    private String mobileNumber;
    private String address;
}
