package com.coditas.multitenantelectricitymanagement.dto.billQuery;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillQueryRequest {

    @NotBlank
    private String query;
}
