package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.clientmeter.ClientMeterRequest;
import com.coditas.multitenantelectricitymanagement.dto.clientmeter.ClientMeterResponse;
import com.coditas.multitenantelectricitymanagement.entity.tenant.ClientMeter;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMeterMapper {

    ClientMeter toEntity(@Valid ClientMeterRequest request);

    ClientMeterResponse toDTO(ClientMeter savedMeter);
}
