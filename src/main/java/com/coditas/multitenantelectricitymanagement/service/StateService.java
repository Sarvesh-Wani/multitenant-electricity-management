package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.dto.state.StateRequest;
import com.coditas.multitenantelectricitymanagement.dto.state.StateResponse;
import com.coditas.multitenantelectricitymanagement.entity.State;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.StateMapper;
import com.coditas.multitenantelectricitymanagement.repository.StateRepostiory;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepostiory stateRepostiory;
    private final StateMapper stateMapper;
    private final UserRepository userRepository;

    public StateResponse createState(StateRequest request) {
        State state = stateMapper.toEntity(request);
        State savedState = stateRepostiory.save(state);
        return stateMapper.toDTO(savedState);
    }

    @Transactional
    public StateResponse assignStateHead(Long stateId, HeadAssignmentRequest request) {

        State state = stateRepostiory.findById(stateId).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        User stateHead = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        if (!stateHead.getRole().equals(Role.STATE_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }
        state.setStateHead(stateHead);
        state.setAssignedAt(Instant.now());

        State updatedState = stateRepostiory.save(state);
        return stateMapper.toDTO(updatedState);
    }
}
