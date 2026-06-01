package com.coditas.multitenantelectricitymanagement.dto;

import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;

    private String email;

    private String password;

    private String tenantId;

    private Role role;
}
