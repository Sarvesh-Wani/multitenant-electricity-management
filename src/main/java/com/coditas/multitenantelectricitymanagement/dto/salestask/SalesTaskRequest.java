package com.coditas.multitenantelectricitymanagement.dto.salestask;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesTaskRequest {

    @NotBlank
    private String task;

    @Nullable
    private Long salesTeamMemberId;
}
