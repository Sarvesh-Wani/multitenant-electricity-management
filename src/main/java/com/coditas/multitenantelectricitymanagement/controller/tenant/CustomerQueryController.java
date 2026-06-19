package com.coditas.multitenantelectricitymanagement.controller.tenant;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.DataRequest;
import com.coditas.multitenantelectricitymanagement.dto.cutomerquery.CustomerQueryRequest;
import com.coditas.multitenantelectricitymanagement.dto.cutomerquery.CustomerQueryResponse;
import com.coditas.multitenantelectricitymanagement.service.CustomerQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(ApiPath.CustomerQuery.BASE)
    public ResponseEntity<ApplicationResponse<CustomerQueryResponse>> raiseCustomerQuery(@PathVariable(name = "customer-id") Long id, @Valid @RequestBody CustomerQueryRequest request) {
        CustomerQueryResponse response = customerQueryService.raiseCustomerQuery(id, request);
        URI location = URI.create(ApiPath.BASE_PATH + ApiPath.CustomerQuery.BASE.replace("{customer-id}", id.toString()));
        return ResponseEntity.created(location).body(
                ApplicationResponse.<CustomerQueryResponse>builder()
                        .success(true)
                        .message("Successfully raised the query by the customer")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('LOCAL_TECHNICIAN')")
    @PatchMapping(ApiPath.CustomerQuery.QUERIES+ApiPath.CustomerQuery.ID+ApiPath.CustomerQuery.RESOLVED)
    public ResponseEntity<ApplicationResponse<CustomerQueryResponse>> updateStatusToResolve(@PathVariable Long id, @Valid @RequestBody DataRequest request) {
        CustomerQueryResponse response = customerQueryService.updateStatustoResolve(id, request);
        return ResponseEntity.ok(
                ApplicationResponse.<CustomerQueryResponse>builder()
                        .success(true)
                        .message("Successfully updated query status to Resolved")
                        .data(response)
                        .build()
        );
    }


    @PreAuthorize("hasRole('LOCAL_TECHNICIAN')")
    @PatchMapping(ApiPath.CustomerQuery.QUERIES+ApiPath.CustomerQuery.ID+ApiPath.CustomerQuery.ESCALATED_M1)
    public ResponseEntity<ApplicationResponse<CustomerQueryResponse>> updateStatusToM1(@PathVariable Long id, @Valid @RequestBody DataRequest request) {
        CustomerQueryResponse response = customerQueryService.updateStatusToM1(id, request);
        return ResponseEntity.ok(
                ApplicationResponse.<CustomerQueryResponse>builder()
                        .success(true)
                        .message("Successfully updated query status ESCAlATED TO M1")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('LOCAL_TECHNICIAN')")
    @PatchMapping(ApiPath.CustomerQuery.QUERIES+ApiPath.CustomerQuery.ID+ApiPath.CustomerQuery.ESCALATED_M2)
    public ResponseEntity<ApplicationResponse<CustomerQueryResponse>> updateStatusToM2(@PathVariable Long id, @Valid @RequestBody DataRequest request) {
        CustomerQueryResponse response = customerQueryService.updateStatusToM2(id, request);
        return ResponseEntity.ok(
                ApplicationResponse.<CustomerQueryResponse>builder()
                        .success(true)
                        .message("Successfully updated query status ESCALATED TO M2")
                        .data(response)
                        .build()
        );
    }

}
