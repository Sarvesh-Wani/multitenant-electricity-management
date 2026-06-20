package com.coditas.multitenantelectricitymanagement.controller.tenant;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.clientmeter.ClientMeterRequest;
import com.coditas.multitenantelectricitymanagement.dto.clientmeter.ClientMeterResponse;
import com.coditas.multitenantelectricitymanagement.service.ClientMeterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class ClientMeterController {

    private final ClientMeterService clientMeterService;

    @PreAuthorize("hasRole('OPERATION_HEAD')")
    @PostMapping(ApiPath.MeterConfig.METER)
    public ResponseEntity<ApplicationResponse<ClientMeterResponse>> addMeter(@Valid @RequestBody ClientMeterRequest request) {
        ClientMeterResponse response = clientMeterService.addMeter(request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.MeterConfig.METER);
        return ResponseEntity.created(location).body(
                ApplicationResponse.<ClientMeterResponse>builder()
                        .success(true)
                        .message("Successfully added a new meter in the inventory")
                        .data(response)
                        .build()
        );
    }
}
