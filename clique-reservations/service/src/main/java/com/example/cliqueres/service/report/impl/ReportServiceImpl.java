package com.example.cliqueres.service.report.impl;

import com.example.cliqueres.domain.enums.FileType;
import com.example.cliqueres.service.report.ReportService;
import com.example.cliqueres.service.report.creator.ReportCreator;
import com.example.cliqueres.service.report.exception.ReportException;
import com.example.cliqueres.service.report.generator.ReportGenerator;
import com.example.cliqueres.service.report.mapper.ReportMapper;
import com.example.cliqueres.service.reservation.ReservationService;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  public static final String REPORTS_EXTENSION = ".rptdesign";
  private final String reportsPath = "reports";

  private final IReportEngine birtEngine;
  private final ReservationService reservationService;
  private final ReportCreator creator;
  private final ReportMapper reportMapper;
  public final List<ReportGenerator> generators;

  public IReportRunnable loadReport(String reportName) throws EngineException {
    final var filename = reportName.concat(REPORTS_EXTENSION);
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream =
        classLoader.getResourceAsStream(reportsPath.concat("/").concat(filename));
    if (inputStream == null) {
      throw new ReportException(String.format("No report found with name: %s", reportName));
    }
    return birtEngine.openReportDesign(inputStream);
  }

  @Override
  public byte[] generateReservationsReport(Long eventId, FileType type, String reportName)
      throws EngineException {
    IReportRunnable reportRunnable = loadReport(reportName);
    final var reservations = reservationService.getAllByEventId(eventId);
    final var reservationDtos = reportMapper.toReservationReportDto(reservations);
    final var reportMap = creator.createReservationReport(reservationDtos);
    return getBytes(type, reportMap, reportRunnable);
  }

  private byte[] getBytes(FileType type, Map<String, Collection<?>> reportMap,
      IReportRunnable reportRunnable) throws EngineException {
    return generators.stream()
        .filter(generator -> generator.canGenerate(type))
        .findFirst()
        .orElseThrow(
            () -> new ReportException(
                String.format("No generator found for type: %s", type.name())))
        .generateReport(reportRunnable, reportMap, birtEngine);
  }
}
