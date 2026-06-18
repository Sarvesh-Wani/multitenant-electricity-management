package com.coditas.multitenantelectricitymanagement.entity.tenant;

import com.coditas.multitenantelectricitymanagement.entity.Customer;
import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@Table(name = "customer_query")
public class CustomerQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "query")
    private String query;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private QueryStatus status;

}
