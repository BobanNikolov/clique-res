package com.example.cliqueres.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.example.cliqueres.domain")
@EnableJpaRepositories(basePackages = "com.example.cliqueres.repository")
@Configuration
public class JpaSpringConfiguration {
}
