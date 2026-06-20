package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DistrictMapper.class)
public interface CityMapper {

    CityResponse toDTO(City savedCity);

    List<CityResponse> toDTOList(List<City> cities);
}
