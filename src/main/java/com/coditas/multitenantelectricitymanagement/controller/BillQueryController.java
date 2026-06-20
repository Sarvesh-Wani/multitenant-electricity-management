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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @PreAuthorize("hasRole('BILLER')")
    @GetMapping(ApiPath.Biller.QUERIES)
    public ResponseEntity<ApplicationResponse<List<BillQueryResponse>>> getAllQueries() {
        List<BillQueryResponse> response = billQueryService.getAllQueries();
        return ResponseEntity.ok(
                ApplicationResponse.<List<BillQueryResponse>>builder()
                        .success(true)
                        .message("Successfully retrieved all the queries")
                        .data(response)
                        .build()
        );
    }

}
