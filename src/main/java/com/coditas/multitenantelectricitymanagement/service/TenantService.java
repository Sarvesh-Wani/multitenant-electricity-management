package com.coditas.multitenantelectricitymanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

    private final DataSource dataSource;

    public void createSchema(String tenantId) {

        if (tenantId == null || !tenantId.matches("^[a-z0-9_]{1,63}$")) {
            throw new IllegalArgumentException(
                    "Invalid tenant ID: " + tenantId);
        }
        log.info("creating tenant schema: {}", tenantId);

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS " + tenantId);
            log.info("Schema created: {}", tenantId);
        } catch (Exception e) {
            throw new RuntimeException("Failed creating a schema");
        }

        Flyway fw = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .schemas(tenantId)
                .defaultSchema(tenantId)
                .load();

        fw.migrate();
    }
}
