package com.coditas.multitenantelectricitymanagement.repository;

import com.coditas.multitenantelectricitymanagement.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
