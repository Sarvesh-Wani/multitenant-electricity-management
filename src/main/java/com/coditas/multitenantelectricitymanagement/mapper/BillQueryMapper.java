package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.billQuery.BillQueryResponse;
import com.coditas.multitenantelectricitymanagement.entity.BillQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface BillQueryMapper {

    BillQueryResponse toDTO(BillQuery savedBillQuery);
}
