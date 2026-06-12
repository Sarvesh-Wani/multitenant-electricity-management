package com.coditas.multitenantelectricitymanagement.dto.customer;

import com.coditas.multitenantelectricitymanagement.dto.user.UserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerRequest {

    @NotNull
    private UserRequest user;

    @Size(min = 10)
    private String mobileNumber;

    @NotBlank
    private String address;

}