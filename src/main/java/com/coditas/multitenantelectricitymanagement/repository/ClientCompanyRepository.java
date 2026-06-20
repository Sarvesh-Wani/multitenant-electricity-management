package com.coditas.multitenantelectricitymanagement.repository;

import com.coditas.multitenantelectricitymanagement.entity.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {

    List<ClientCompany> findBySalesTeamMemberId(Long id);
}
