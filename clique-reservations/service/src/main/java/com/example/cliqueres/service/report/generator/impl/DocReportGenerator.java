package com.example.cliqueres.service.report.generator.impl;


import static com.example.cliqueres.domain.enums.FileType.DOC;

import com.example.cliqueres.domain.enums.FileType;
import com.example.cliqueres.service.report.generator.ReportGenerator;
import org.springframework.stereotype.Component;

/**
 * Doc report generator.
 */
@Component
public class DocReportGenerator implements ReportGenerator {
  @Override
  public boolean canGenerate(FileType type) {
    return DOC == type;
  }

  @Override
  public FileType getReportType() {
    return DOC;
  }
}
