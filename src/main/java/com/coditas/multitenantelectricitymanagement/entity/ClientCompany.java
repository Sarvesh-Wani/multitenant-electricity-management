package com.coditas.multitenantelectricitymanagement.entity;

import com.coditas.multitenantelectricitymanagement.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client_company", schema = "public")
public class ClientCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "tenant_id", unique = true)
    private String tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompanyStatus status;

}
