package com.coditas.multitenantelectricitymanagement.dto.city;

import com.coditas.multitenantelectricitymanagement.dto.district.DistrictResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CityResponse {

    private Long id;

    private String name;

    private DistrictResponse district;

    private UserResponse cityHead;

    private Instant assignedAt;

    private Instant createdAt;
}
