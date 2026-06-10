package com.coditas.multitenantelectricitymanagement.dto.area;

import com.coditas.multitenantelectricitymanagement.dto.city.CityResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AreaResponse {
    private Long id;

    private String name;

    private CityResponse city;

    private UserResponse biller;

    private UserResponse technician;

    private Instant createdAt;
}
