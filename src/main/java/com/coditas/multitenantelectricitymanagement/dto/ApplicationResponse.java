package com.coditas.multitenantelectricitymanagement.dto;

import lombok.*;
import org.springframework.data.jpa.repository.Meta;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Object errors;
    private Meta meta;
}
