package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.bpostate.BPOStateResponse;
import com.coditas.multitenantelectricitymanagement.entity.tenant.BPOState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, StateMapper.class})
public interface BPOStateMapper {
    BPOStateResponse toDTO(BPOState savedBPOState);
}
