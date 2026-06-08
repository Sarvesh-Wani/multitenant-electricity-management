package com.coditas.multitenantelectricitymanagement.dto.state;

import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class StateResponse {
    private Long id;

    private String name;

    private UserResponse stateHead;

    private Instant assignedAt;

    private Instant createdAt;
}
