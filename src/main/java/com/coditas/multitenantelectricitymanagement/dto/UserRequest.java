package com.coditas.multitenantelectricitymanagement.dto;

import com.coditas.multitenantelectricitymanagement.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    private String tenantId;

    private Role role;
}
