package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.area.AreaRequest;
import com.coditas.multitenantelectricitymanagement.dto.area.AreaResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.entity.Area;
import com.coditas.multitenantelectricitymanagement.entity.City;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.AreaMapper;
import com.coditas.multitenantelectricitymanagement.repository.AreaRepository;
import com.coditas.multitenantelectricitymanagement.repository.CityRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public AreaResponse registerArea(Long cityId, AreaRequest request) {
        log.info("Processing request to register area with name {} for city id {} ", request.getName(), cityId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String cityHeadName = authentication.getName();

        User cityHead = userRepository.findByUsername(cityHeadName).orElseThrow(
                () -> {
                    log.warn("The city head does not exist with id {} ", cityId);
                    throw new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND);
                }
        );

        if (!cityHead.getRole().equals(Role.CITY_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        Area area = new Area();
        area.setName(request.getName());
        City city = cityRepository.findById(cityId).orElseThrow(
                () -> {
                    log.warn("registration failed as the city with id {} does not found ", cityId);
                    throw new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND);
                }
        );
        area.setCity(city);
        Area savedArea = areaRepository.save(area);
        log.info("Successfully registered area with id {}", savedArea.getId());
        return areaMapper.toDTO(savedArea);
    }

    public AreaResponse assignTechnicians(Long cityId, Long areaId, HeadAssignmentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String cityHeadName = authentication.getName();

        User cityHead = userRepository.findByUsername(cityHeadName).orElseThrow(
                () -> {
                    log.warn("The city head does not exist with id {} ", cityId);
                    throw new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND);
                }
        );

        if (!cityHead.getRole().equals(Role.CITY_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        Area area = areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND));
        User technician = userRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND));
        if (!technician.getRole().equals(Role.LOCAL_TECHNICIAN)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }
        area.setTechnician(technician);
        Area updatedArea = areaRepository.save(area);
        log.info("Successfully assigned technician to area {}", area.getId());
        return areaMapper.toDTO(updatedArea);
    }

    public AreaResponse assignBiller(Long cityId, Long areaId, HeadAssignmentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String cityHeadName = authentication.getName();

        User cityHead = userRepository.findByUsername(cityHeadName).orElseThrow(
                () -> {
                    log.warn("The city head does not exist with id {} ", cityId);
                    throw new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND);
                }
        );

        if (!cityHead.getRole().equals(Role.CITY_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        Area area = areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND));
        User biller = userRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND));
        if (!biller.getRole().equals(Role.BILLER)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }
        area.setBiller(biller);
        Area updatedArea = areaRepository.save(area);
        log.info("Successfully assigned biller to area {}", area.getId());
        return areaMapper.toDTO(updatedArea);

    }
}
