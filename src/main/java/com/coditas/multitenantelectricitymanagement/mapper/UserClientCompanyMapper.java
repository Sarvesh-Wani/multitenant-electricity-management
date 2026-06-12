package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.userclientcompany.UserClientCompanyResponse;
import com.coditas.multitenantelectricitymanagement.entity.UserClientCompany;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserClientCompanyMapper {
    UserClientCompanyResponse toDTO(UserClientCompany savedUserClient);
}
