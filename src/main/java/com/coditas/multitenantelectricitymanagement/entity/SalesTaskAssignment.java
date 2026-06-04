package com.coditas.multitenantelectricitymanagement.entity;

import com.coditas.multitenantelectricitymanagement.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_task_assignment", schema = "public")
public class SalesTaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @ManyToOne
    @JoinColumn(name = "sales_member_id")
    private User salesMember;

    @Column(name = "task", nullable = false)
    private String task;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "public.task_status")
    private TaskStatus status;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "created_at")
    private Instant createdAt;

}
