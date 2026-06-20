package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.salestask.SalesTaskRequest;
import com.coditas.multitenantelectricitymanagement.dto.salestask.SalesTaskResponse;
import com.coditas.multitenantelectricitymanagement.entity.SalesTask;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface SalesTaskMapper {

    SalesTaskResponse toDTO(SalesTask savedTask);

    SalesTask toEntity(SalesTaskRequest request);


    List<SalesTaskResponse> toDTOList(List<SalesTask> salesTasks);
}
