package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.cutomerquery.CustomerQueryResponse;
import com.coditas.multitenantelectricitymanagement.entity.tenant.CustomerQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface CustomerQueryMapper {
    CustomerQueryResponse toDTO(CustomerQuery savedQuery);
}
