package com.coditas.multitenantelectricitymanagement.dto.district;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictRequest {

    @NotBlank
    private String name;

    private String state;
}
