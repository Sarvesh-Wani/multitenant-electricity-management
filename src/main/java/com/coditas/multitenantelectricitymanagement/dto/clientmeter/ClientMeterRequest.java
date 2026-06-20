package com.coditas.multitenantelectricitymanagement.dto.clientmeter;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientMeterRequest {
    @NotBlank
    private String meterType;

    private Double ratePerUnit;
}
