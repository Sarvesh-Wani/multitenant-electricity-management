package com.coditas.multitenantelectricitymanagement.dto.billing;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BillingRequest {

    @Positive
    @NotNull
    private Double amount;

    private Instant dueDate;
}
