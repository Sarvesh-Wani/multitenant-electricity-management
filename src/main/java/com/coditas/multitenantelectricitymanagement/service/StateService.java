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
import com.coditas.multitenantelectricitymanagement.repository.StateRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private final UserRepository userRepository;

    public StateResponse createState(StateRequest request) {
        State state = stateMapper.toEntity(request);
        State savedState = stateRepository.save(state);
        return stateMapper.toDTO(savedState);
    }

    @Transactional
    public StateResponse assignStateHead(Long stateId, HeadAssignmentRequest request) {

        State state = stateRepository.findById(stateId).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.STATE_NOT_FOUND)
        );

        User stateHead = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.USER_NOT_FOUND)
        );

        if (!stateHead.getRole().equals(Role.STATE_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }
        state.setStateHead(stateHead);
        state.setAssignedAt(Instant.now());

        State updatedState = stateRepository.save(state);
        return stateMapper.toDTO(updatedState);
    }

    public StateResponse retrieveStateById(Long id) {
        State state = stateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.STATE_NOT_FOUND)
        );
        return stateMapper.toDTO(state);
    }

    public List<StateResponse> retrieveAllStates() {
        List<State> states = stateRepository.findAll();
        return stateMapper.toDTOList(states);
    }
}
