package com.coditas.multitenantelectricitymanagement.dto.billing;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BillingRequest {

    private Double amount;

    private Instant dueDate;
}
