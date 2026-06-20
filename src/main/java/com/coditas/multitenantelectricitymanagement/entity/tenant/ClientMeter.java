package com.coditas.multitenantelectricitymanagement.entity.tenant;

import com.coditas.multitenantelectricitymanagement.enums.MeterType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "client_meter")
public class ClientMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private MeterType meterType;

    @Column(name = "rate_per_unit")
    private Double ratePerUnit;

}
