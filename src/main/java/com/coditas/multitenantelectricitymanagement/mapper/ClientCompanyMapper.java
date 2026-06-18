package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.company.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.entity.ClientCompany;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, UserMapper.class})
public interface ClientCompanyMapper {
    CompanyResponse toDTO(ClientCompany savedCompany);
}
