package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.city.CityRequest;
import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.dto.state.HeadAssignmentRequest;
import com.coditas.multitenantelectricitymanagement.entity.City;
import com.coditas.multitenantelectricitymanagement.entity.District;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.DuplicateResourceException;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.CityMapper;
import com.coditas.multitenantelectricitymanagement.repository.CityRepository;
import com.coditas.multitenantelectricitymanagement.repository.DistrictRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CityMapper cityMapper;
    private final UserRepository userRepository;

    public CityResponse createCity(CityRequest request) {

        if (cityRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(ExceptionConstants.DUPLICATE_RESOURCE);
        }
        City city = new City();
        city.setName(request.getName());

        District district = districtRepository.findByName(request.getDistrict()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        city.setDistrict(district);
        City savedCity = cityRepository.save(city);
        return cityMapper.toDTO(savedCity);
    }

    @Transactional
    public CityResponse assignCityHead(Long cityId, HeadAssignmentRequest request) {

        City city = cityRepository.findById(cityId).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        User cityHead = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        if (!cityHead.getRole().equals(Role.CITY_HEAD)) {
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        city.setCityHead(cityHead);
        city.setAssignedAt(Instant.now());

        City savedCity = cityRepository.save(city);
        return cityMapper.toDTO(savedCity);

    }
}
