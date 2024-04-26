package com.example.cliqueres.service.report;

import com.example.cliqueres.domain.enums.FileType;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportRunnable;

/**
 * The service for the reports.
 */
public interface ReportService {

  IReportRunnable loadReport(String reportName) throws EngineException;

  byte[] generateReservationsReport(Long eventId, FileType type, String reportName)
      throws EngineException;
}