package com.example.cliqueres.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
 public class ResourceConfig implements WebMvcConfigurer {

  @Bean
  public ResourceLoader resourceLoader() {
    return new org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext();
  }
}
