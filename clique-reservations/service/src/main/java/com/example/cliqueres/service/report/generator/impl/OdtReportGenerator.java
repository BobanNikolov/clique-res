package com.example.cliqueres.service.report.generator.impl;


import static com.example.cliqueres.domain.enums.FileType.ODT;

import com.example.cliqueres.domain.enums.FileType;
import com.example.cliqueres.service.report.generator.ReportGenerator;
import org.springframework.stereotype.Component;

/**
 * Odt report generator.
 */
@Component
public class OdtReportGenerator implements ReportGenerator {
  @Override
  public boolean canGenerate(FileType type) {
    return ODT == type;
  }

  @Override
  public FileType getReportType() {
    return ODT;
  }
}
