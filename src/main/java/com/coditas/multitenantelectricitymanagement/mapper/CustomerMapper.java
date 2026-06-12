package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerRequest;
import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerResponse;
import com.coditas.multitenantelectricitymanagement.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AreaMapper.class})
public interface CustomerMapper {

    Customer toEntity(CustomerRequest request);

    CustomerResponse toDTO(Customer savedCustomer);
}
