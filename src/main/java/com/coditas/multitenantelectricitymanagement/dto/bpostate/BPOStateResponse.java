package com.coditas.multitenantelectricitymanagement.dto.bpostate;

import com.coditas.multitenantelectricitymanagement.dto.state.StateResponse;
import com.coditas.multitenantelectricitymanagement.dto.tenant.employee.EmployeeResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BPOStateResponse {
    private EmployeeResponse bpo;
    private StateResponse state;
}
