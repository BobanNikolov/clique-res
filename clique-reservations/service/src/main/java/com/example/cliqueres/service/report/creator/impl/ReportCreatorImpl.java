package com.example.cliqueres.service.report.creator.impl;

import com.example.cliqueres.service.report.creator.ReportCreator;
import com.example.cliqueres.service.report.dto.ReservationReportDto;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ReportCreatorImpl implements ReportCreator {

  @Override
  public Map<String, Collection<?>> createReservationReport(
      Set<ReservationReportDto> reservations) {
    Map<String, Collection<?>> reportMap = new HashMap<>();

    reportMap.putIfAbsent("reservations", reservations);

    return reportMap;
  }
}
