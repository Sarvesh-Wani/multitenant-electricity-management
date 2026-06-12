package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer", schema = "public")
public class Customer {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "address", nullable = false)
    private String address;
}
