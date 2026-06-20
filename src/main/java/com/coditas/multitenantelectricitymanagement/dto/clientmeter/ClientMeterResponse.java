package com.coditas.multitenantelectricitymanagement.dto.clientmeter;

import com.coditas.multitenantelectricitymanagement.enums.MeterType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientMeterResponse {
    private MeterType meterType;

    private Double ratePerUnit;
}
