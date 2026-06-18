package com.coditas.multitenantelectricitymanagement.entity.tenant;

import com.coditas.multitenantelectricitymanagement.entity.Employee;
import com.coditas.multitenantelectricitymanagement.entity.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="bpo_states")
public class BPOState {

    @EmbeddedId
    private BPOStateID id;

    @ManyToOne
    @JoinColumn(name = "bpo_id")
    @MapsId("bpoId")
    private Employee bpo;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @MapsId("stateId")
    private State state;
}
