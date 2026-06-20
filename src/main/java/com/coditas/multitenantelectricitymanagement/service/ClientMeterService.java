package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.dto.clientmeter.ClientMeterRequest;
import com.coditas.multitenantelectricitymanagement.dto.clientmeter.ClientMeterResponse;
import com.coditas.multitenantelectricitymanagement.entity.tenant.ClientMeter;
import com.coditas.multitenantelectricitymanagement.enums.MeterType;
import com.coditas.multitenantelectricitymanagement.mapper.ClientMeterMapper;
import com.coditas.multitenantelectricitymanagement.repository.ClientMeterRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientMeterService {

    private final ClientMeterRepository clientMeterRepository;
    private final ClientMeterMapper clientMeterMapper;

    public ClientMeterResponse addMeter(@Valid ClientMeterRequest request) {
        ClientMeter meter = new ClientMeter();
        String req = request.getMeterType().toLowerCase();
        if (req.equals("solar")) {
            meter.setMeterType(MeterType.SOLAR);
        } else if (req.equals("industrial")) {
            meter.setMeterType(MeterType.INDUSTRIAL);
        } else if (req.equals("household")) {
            meter.setMeterType(MeterType.HOUSEHOLD);
        }
        meter.setRatePerUnit(request.getRatePerUnit());
        ClientMeter savedMeter = clientMeterRepository.save(meter);
        return clientMeterMapper.toDTO(savedMeter);
    }
}
