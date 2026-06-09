package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
import com.coditas.multitenantelectricitymanagement.entity.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DistrictMapper {

    DistrictResponse toDTO(District savedDistrict);
}
