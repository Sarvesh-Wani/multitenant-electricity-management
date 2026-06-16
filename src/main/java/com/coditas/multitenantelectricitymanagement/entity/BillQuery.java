package com.coditas.multitenantelectricitymanagement.entity;

import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@Table(name = "bill_query", schema = "public")
public class BillQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "biller_id")
    private User biller;

    @Column(name = "query")
    private String query;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "public.query_status")
    private QueryStatus status;

}
