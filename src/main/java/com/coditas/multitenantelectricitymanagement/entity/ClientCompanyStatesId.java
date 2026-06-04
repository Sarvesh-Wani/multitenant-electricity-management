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
public class ClientCompanyStatesId implements Serializable {

    private Long clientCompanyId;

    private Long stateId;
}
