package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client_company_states", schema = "public")
public class ClientCompanyStates {

    @EmbeddedId
    private ClientCompanyStatesId id;

    @ManyToOne
    @JoinColumn(name = "client_company_id")
    @MapsId("clientCompanyId")
    private ClientCompany clientCompany;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @MapsId("stateId")
    private State state;
}
