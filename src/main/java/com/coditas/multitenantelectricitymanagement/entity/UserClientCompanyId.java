package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserClientCompanyId implements Serializable {

    private Long userId;

    private Long clientCompanyId;
}
