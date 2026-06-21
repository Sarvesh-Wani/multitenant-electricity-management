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
import com.coditas.multitenantelectricitymanagement.repository.StateRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final UserRepository userRepository;
    private final StateRepository stateRepostiory;

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

        if (!districtHead.getRole().equals(Role.DISTRICT_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        district.setDistrictHead(districtHead);
        district.setAssignedAt(Instant.now());

        District updatedDistrict = districtRepository.save(district);
        return districtMapper.toDTO(updatedDistrict);
    }

    public DistrictResponse retrieveDistrictById(Long id) {
        District district = districtRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.DISTRICT_NOT_FOUND)
        );
        return districtMapper.toDTO(district);
    }

    public List<DistrictResponse> retrieveAllDistricts() {
        List<District> districts = districtRepository.findAll();
        return districtMapper.toDTOList(districts);
    }
}
