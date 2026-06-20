package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.company.CompanyResponse;
import com.coditas.multitenantelectricitymanagement.entity.ClientCompany;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, UserMapper.class})
public interface ClientCompanyMapper {
    CompanyResponse toDTO(ClientCompany savedCompany);

    List<CompanyResponse> toDTOList(List<ClientCompany> companies);
}
