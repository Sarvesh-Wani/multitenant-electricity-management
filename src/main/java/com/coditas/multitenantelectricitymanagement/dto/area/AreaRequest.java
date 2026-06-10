package com.coditas.multitenantelectricitymanagement.dto.area;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRequest {

    @NotBlank
    private String name;
}
