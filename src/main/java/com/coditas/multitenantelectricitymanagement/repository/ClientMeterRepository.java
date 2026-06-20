package com.coditas.multitenantelectricitymanagement.repository;

import com.coditas.multitenantelectricitymanagement.entity.tenant.ClientMeter;
import com.coditas.multitenantelectricitymanagement.enums.MeterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientMeterRepository extends JpaRepository<ClientMeter, MeterType> {
}
