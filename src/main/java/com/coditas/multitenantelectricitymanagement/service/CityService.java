package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.city.CityRequest;
import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CityMapper cityMapper;
    private final UserRepository userRepository;

    public CityResponse createCity(CityRequest request) {
        log.info("Processing for creating city with name {}", request.getName());
        if (cityRepository.existsByName(request.getName())) {
            log.warn("City creation failed, with name {} already exist", request.getName());
            throw new DuplicateResourceException(ExceptionConstants.DUPLICATE_RESOURCE);
        }
        City city = new City();
        city.setName(request.getName());

        District district = districtRepository.findByName(request.getDistrict()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        city.setDistrict(district);
        City savedCity = cityRepository.save(city);
        log.info("Successfully saved city with generated id {}", savedCity.getId());
        return cityMapper.toDTO(savedCity);
    }

    @Transactional
    public CityResponse assignCityHead(Long cityId, HeadAssignmentRequest request) {

        log.info("Processing for assigning city head for city with id {}", cityId);
        City city = cityRepository.findById(cityId).orElseThrow(() -> {
                    log.warn("Assignment failed, as city does not exist for Id {}", cityId);
                    return new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND);
                }
        );

        User cityHead = userRepository.findById(request.getId()).orElseThrow(() -> {
                    log.warn("Assignment failed, as city head does not exist for Id {}", request.getId());
                    return new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND);
                }
        );

        if (!cityHead.getRole().equals(Role.CITY_HEAD)) {
            log.warn("Assignment failed as provided city head is not having Role as CITY_HEAD");
            throw new RoleMisMatchException(ExceptionConstants.ROLE_MISMATCH);
        }

        city.setCityHead(cityHead);
        city.setAssignedAt(Instant.now());

        City savedCity = cityRepository.save(city);
        log.info("Successfully assigned city head to the city with id {}", savedCity.getId());
        return cityMapper.toDTO(savedCity);
    }

    public CityResponse retrieveCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.CITY_NOT_FOUND)
        );
        return cityMapper.toDTO(city);
    }

    public List<CityResponse> retrieveAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.toDTOList(cities);
    }

}
