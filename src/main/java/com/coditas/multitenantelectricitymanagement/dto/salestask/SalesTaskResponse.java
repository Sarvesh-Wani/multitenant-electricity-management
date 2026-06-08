package com.coditas.multitenantelectricitymanagement.dto.salestask;

import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Builder
public class SalesTaskResponse {

    private String id;

    private UserResponse manager;

    private UserResponse salesMember;

    private String task;

    private TaskStatus status;

    private Instant assignedAt;

    private Instant createdAt;
}
