package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_client_company", schema = "public")
public class UserClientCompany {

    @EmbeddedId
    private UserClientCompanyId id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_company_id")
    @MapsId("clientCompanyId")
    private ClientCompany clientCompany;
}
