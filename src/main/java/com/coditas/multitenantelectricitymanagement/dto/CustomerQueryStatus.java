package com.coditas.multitenantelectricitymanagement.dto;

import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerQueryStatus {
    private QueryStatus status;
}
