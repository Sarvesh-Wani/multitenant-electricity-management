package com.coditas.multitenantelectricitymanagement.repository;

import com.coditas.multitenantelectricitymanagement.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepostiory extends JpaRepository<State, Long> {
    Optional<State> findByName(String state);
}
