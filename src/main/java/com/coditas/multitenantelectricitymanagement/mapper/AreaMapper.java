package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.area.AreaResponse;
import com.coditas.multitenantelectricitymanagement.entity.Area;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AreaMapper {


    AreaResponse toDTO(Area savedArea);

    List<AreaResponse> toDTOList(List<Area> areas);
}
