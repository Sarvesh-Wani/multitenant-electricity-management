package com.coditas.multitenantelectricitymanagement.tenant;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class HibernateConfig {

    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.coditas.multitenantelectricitymanagement.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        props.put(Environment.SHOW_SQL, true);
        props.put(Environment.FORMAT_SQL, true);
        props.put(Environment.DEFAULT_SCHEMA, "public");

        props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);

        factory.setJpaProperties(props);
        return factory;
    }

    @Bean
    public org.springframework.orm.jpa.JpaTransactionManager transactionManager(
            jakarta.persistence.EntityManagerFactory emf) {
        return new org.springframework.orm.jpa.JpaTransactionManager(emf);
    }
}
