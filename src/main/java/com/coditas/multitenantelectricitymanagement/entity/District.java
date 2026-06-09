package com.coditas.multitenantelectricitymanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "districts", schema = "public")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "district_head_id")
    private User districtHead;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
}
