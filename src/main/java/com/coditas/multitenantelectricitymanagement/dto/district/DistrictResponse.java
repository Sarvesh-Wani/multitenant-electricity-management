package com.coditas.multitenantelectricitymanagement.dto.district;

import com.coditas.multitenantelectricitymanagement.dto.state.StateResponse;
import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class DistrictResponse {

    private Long id;

    private String name;

    private StateResponse state;

    private UserResponse districtHead;

    private Instant assignedAt;

    private Instant createdAt;
}
