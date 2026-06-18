package com.coditas.multitenantelectricitymanagement.dto.bpostate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BPOStateRequest {
    @NotNull
    private Long bpo;
    @NotNull
    private Long state;
}
