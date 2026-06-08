package com.coditas.multitenantelectricitymanagement.dto.salestask;

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

    private String manager;

    private String salesMember;

    private String task;

    private TaskStatus status;

    private Instant assignedAt;

    private Instant createdAt;
}
