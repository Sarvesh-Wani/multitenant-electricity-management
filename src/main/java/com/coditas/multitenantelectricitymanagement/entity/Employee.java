package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "city")
    private String city;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

}
