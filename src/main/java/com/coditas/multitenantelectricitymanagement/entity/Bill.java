package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bills", schema = "public")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;

    @Column(name = "bill_date")
    private Instant billDate;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;
}
