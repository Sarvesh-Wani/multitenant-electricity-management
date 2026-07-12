package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.state.StateRequest;
import com.coditas.multitenantelectricitymanagement.dto.state.StateResponse;
import com.coditas.multitenantelectricitymanagement.entity.State;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface StateMapper {

    State toEntity(StateRequest request);

    StateResponse toDTO(State savedState);

    List<StateResponse> toDTOList(List<State> states);
}
