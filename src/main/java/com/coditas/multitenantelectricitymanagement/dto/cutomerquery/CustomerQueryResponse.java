package com.coditas.multitenantelectricitymanagement.dto.cutomerquery;

import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerResponse;
import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerQueryResponse {

    private Long id;

    private String query;

    private CustomerResponse customer;

    private QueryStatus status;
}
