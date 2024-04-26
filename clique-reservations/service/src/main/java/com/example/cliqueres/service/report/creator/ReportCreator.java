package com.example.cliqueres.service.report.creator;

import com.example.cliqueres.service.report.dto.ReservationReportDto;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface ReportCreator {
  Map<String, Collection<?>> createReservationReport(Set<ReservationReportDto> reservations);
}
