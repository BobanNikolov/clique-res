package com.example.cliqueres.service.report.generator.impl;


import static com.example.cliqueres.domain.enums.FileType.PDF;

import com.example.cliqueres.domain.enums.FileType;
import com.example.cliqueres.service.report.generator.ReportGenerator;
import org.springframework.stereotype.Component;

/**
 * Pdf report generator.
 */
@Component
public class PdfReportGenerator implements ReportGenerator {

  @Override
  public boolean canGenerate(FileType type) {
    return PDF == type;
  }

  @Override
  public FileType getReportType() {
    return PDF;
  }
}
