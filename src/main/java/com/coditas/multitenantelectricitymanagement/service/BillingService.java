package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.billing.BillingRequest;
import com.coditas.multitenantelectricitymanagement.dto.billing.BillingResponse;
import com.coditas.multitenantelectricitymanagement.entity.Billing;
import com.coditas.multitenantelectricitymanagement.entity.Customer;
import com.coditas.multitenantelectricitymanagement.enums.BillStatus;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.mapper.BillingMapper;
import com.coditas.multitenantelectricitymanagement.repository.BillingRepository;
import com.coditas.multitenantelectricitymanagement.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final CustomerRepository customerRepository;
    private final BillingMapper billingMapper;

    public BillingResponse generateBill(Long customerId, @Valid BillingRequest request) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.CUSTOMER_NOT_FOUND)
        );

        Billing billing = billingMapper.toEntity(request);
        billing.setCustomer(customer);
        billing.setStatus(BillStatus.PENDING);
        Billing savedBilling = billingRepository.save(billing);
        return billingMapper.toDTO(savedBilling);
    }
}
