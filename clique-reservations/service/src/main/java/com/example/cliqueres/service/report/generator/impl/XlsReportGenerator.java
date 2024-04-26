package com.example.cliqueres.service.report.generator.impl;


import static com.example.cliqueres.domain.enums.FileType.XLS;

import com.example.cliqueres.domain.enums.FileType;
import com.example.cliqueres.service.report.generator.ReportGenerator;
import org.springframework.stereotype.Component;

/**
 * Xls report generator.
 */
@Component
public class XlsReportGenerator implements ReportGenerator {
  @Override
  public boolean canGenerate(FileType type) {
    return XLS == type;
  }

  @Override
  public FileType getReportType() {
    return XLS;
  }
}
