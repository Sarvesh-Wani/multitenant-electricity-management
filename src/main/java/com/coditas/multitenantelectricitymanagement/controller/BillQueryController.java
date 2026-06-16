package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.billQuery.BillQueryRequest;
import com.coditas.multitenantelectricitymanagement.dto.billQuery.BillQueryResponse;
import com.coditas.multitenantelectricitymanagement.service.BillQueryService;
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
@RequestMapping(ApiPath.Biller.BASE)
@RequiredArgsConstructor
public class BillQueryController {

    private final BillQueryService billQueryService;

    @PreAuthorize("hasRole('BILLER')")
    @PostMapping(ApiPath.Biller.QUERIES)
    public ResponseEntity<ApplicationResponse<BillQueryResponse>> issueQuery(@Valid @RequestBody BillQueryRequest request) {
        BillQueryResponse response = billQueryService.issueQuery(request);
        URI location = URI.create(ApiPath.Biller.BASE + ApiPath.Biller.QUERIES + "/" + response.getId());
        return ResponseEntity.created(location).body(
                ApplicationResponse.<BillQueryResponse>builder()
                        .success(true)
                        .message("Successfully issued query")
                        .data(response)
                        .build()
        );
    }

}
