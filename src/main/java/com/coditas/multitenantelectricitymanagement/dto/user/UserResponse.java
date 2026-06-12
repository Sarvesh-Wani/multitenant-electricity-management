package com.coditas.multitenantelectricitymanagement.dto.user;

import com.coditas.multitenantelectricitymanagement.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Role role;
    private boolean isActive;
    private Instant createdAt;
}
