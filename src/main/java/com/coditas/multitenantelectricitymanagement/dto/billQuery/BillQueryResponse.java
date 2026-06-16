package com.coditas.multitenantelectricitymanagement.dto.billQuery;

import com.coditas.multitenantelectricitymanagement.dto.user.UserResponse;
import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillQueryResponse {

    private Long id;

    private UserResponse biller;

    private String query;

    private QueryStatus status;
}
