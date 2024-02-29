package com.example.cliqures.service.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@EnableJpaRepositories
@EntityScan
@EnableTransactionManagement
public class ServiceTestConfig {
}
