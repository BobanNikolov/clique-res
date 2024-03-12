package com.example.cliqueres.service.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateAdjuster {
  private final String DATE_FORMATTER = "dd.MM.yyyy";

  public static String fromLocalDateToString(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
  }

  public static LocalDate fromStringToLocalDate(String date) {
    if (!StringUtils.hasLength(date)) {
      return null;
    }
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMATTER));
  }
}
