package com.roche.ddd.infrastructure.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.roche"})
@EnableJpaRepositories(basePackages = {"com.roche.ddd.infrastructure.repository.jpa"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
