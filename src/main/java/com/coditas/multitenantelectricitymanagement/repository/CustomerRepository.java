package com.coditas.multitenantelectricitymanagement.repository;

import com.coditas.multitenantelectricitymanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
