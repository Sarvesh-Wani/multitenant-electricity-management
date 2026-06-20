package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
import com.coditas.multitenantelectricitymanagement.entity.District;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DistrictMapper {

    DistrictResponse toDTO(District savedDistrict);

    List<DistrictResponse> toDTOList(List<District> districts);
}
