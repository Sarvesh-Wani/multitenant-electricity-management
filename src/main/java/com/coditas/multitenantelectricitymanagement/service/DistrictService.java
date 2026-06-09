package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.district.DistrictRequest;
import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.entity.District;
import com.coditas.multitenantelectricitymanagement.entity.State;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.DistrictMapper;
import com.coditas.multitenantelectricitymanagement.repository.DistrictRepository;
import com.coditas.multitenantelectricitymanagement.repository.StateRepostiory;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final UserRepository userRepository;
    private final StateRepostiory stateRepostiory;

    public DistrictResponse createDistrict(DistrictRequest request) {
        District district = new District();
        district.setName(request.getName());
        State state = stateRepostiory.findByName(request.getState()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );
        district.setState(state);
        District savedDistrict = districtRepository.save(district);
        return districtMapper.toDTO(savedDistrict);
    }

    @Transactional
    public DistrictResponse assignDistrictHead(Long districtId, @Valid HeadAssignmentRequest request) {
        District district = districtRepository.findById(districtId).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        User districtHead = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        if(!districtHead.getRole().equals(Role.DISTRICT_HEAD)){
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        district.setDistrictHead(districtHead);
        district.setAssignedAt(Instant.now());

        District updatedDistrict = districtRepository.save(district);
        return districtMapper.toDTO(updatedDistrict);
    }
}
