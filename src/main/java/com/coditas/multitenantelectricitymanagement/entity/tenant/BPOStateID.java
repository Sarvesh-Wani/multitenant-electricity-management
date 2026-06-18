package com.coditas.multitenantelectricitymanagement.entity.tenant;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BPOStateID implements Serializable {

    private Long bpoId;

    private Long stateId;
}
