package com.coditas.multitenantelectricitymanagement.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    public static final String DEFAULT_SCHEMA ="public";

    @Override
    public Object resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getTenant();
        return (tenant != null && !tenant.isBlank()) ? tenant : DEFAULT_SCHEMA;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
