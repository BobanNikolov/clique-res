package com.example.cliqueres.endpoint.report;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

import com.example.cliqueres.domain.enums.FileType;
import com.example.cliqueres.service.report.ReportService;
import com.example.cliqueres.service.report.exception.ReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

  private static final String RESERVATIONS_REPORT_NAME = "CliqueReservations";

  private final ReportService reportService;

  @PostMapping(value = "/reservations", produces = {APPLICATION_OCTET_STREAM_VALUE})
  public ResponseEntity<byte[]> generateReservationsReport(
      @RequestParam("event_id") Long eventId,
      @RequestParam("type") FileType type) {
    try {
      final var report = reportService.generateReservationsReport(eventId, type,
          RESERVATIONS_REPORT_NAME);
      return ResponseEntity.ok()
          .header(CONTENT_DISPOSITION,
              "attachment; filename=" + RESERVATIONS_REPORT_NAME + "." + type.toString()
                  .toLowerCase())
          .body(report);
    } catch (Exception e) {
      throw new ReportException(e.getMessage());
    }
  }
}
