package com.coditas.multitenantelectricitymanagement.dto.billing;

import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerResponse;
import com.coditas.multitenantelectricitymanagement.enums.BillStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BillingResponse {

    private Long id;

    private CustomerResponse customer;

    private Double amount;

    private Instant generatedAt;

    private BillStatus status;

    private Instant dueDate;

}
