package com.example.cliqueres.service.config;

import lombok.SneakyThrows;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfiguration {
  @Bean
  @SneakyThrows
  public IReportEngine reportEngine() {
    EngineConfig config = new EngineConfig();
    Platform.startup(config);

    IReportEngineFactory factory = (IReportEngineFactory) Platform
        .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
    IReportEngine birtEngine = factory.createReportEngine(config);

    return birtEngine;
  }
}
