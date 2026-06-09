package com.coditas.multitenantelectricitymanagement.repository;

import com.coditas.multitenantelectricitymanagement.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String name);
}
