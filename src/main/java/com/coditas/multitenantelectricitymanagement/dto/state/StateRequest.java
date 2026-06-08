package com.coditas.multitenantelectricitymanagement.dto.state;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateRequest {

    @NotBlank
    private String name;

}
