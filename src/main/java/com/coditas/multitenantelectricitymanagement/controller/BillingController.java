package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.billing.BillingRequest;
import com.coditas.multitenantelectricitymanagement.dto.billing.BillingResponse;
import com.coditas.multitenantelectricitymanagement.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.Biller.CUSTOMER)
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PreAuthorize("hasRole('BILLER')")
    @PostMapping(ApiPath.Biller.ID + ApiPath.Biller.BILL)
    public ResponseEntity<ApplicationResponse<BillingResponse>> billing(@PathVariable(name = "id") Long customerId, @Valid @RequestBody BillingRequest request) {

        BillingResponse response = billingService.generateBill(customerId, request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.Biller.ID + ApiPath.Biller.BILL.replace("{id}", String.valueOf(response.getId())));
        return ResponseEntity.created(location).body(
                ApplicationResponse.<BillingResponse>builder()
                        .success(true)
                        .message("Successfully generated bill")
                        .data(response)
                        .build()
        );


    }

}
