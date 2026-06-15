package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.billing.BillingRequest;
import com.coditas.multitenantelectricitymanagement.dto.billing.BillingResponse;
import com.coditas.multitenantelectricitymanagement.entity.Billing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface BillingMapper {
    Billing toEntity(BillingRequest request);

    BillingResponse toDTO(Billing savedBilling);
}
