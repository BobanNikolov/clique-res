package com.example.cliqueres.service.report.mapper;

import com.example.cliqueres.service.report.dto.ReservationReportDto;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

  public Set<ReservationReportDto> toReservationReportDto(List<ReservationDto> reservations) {
    Set<ReservationReportDto> reservationReportDtos = new HashSet<>();
    if (!reservations.isEmpty()) {
      for (var reservation : reservations) {
        ReservationReportDto reservationReportDto = new ReservationReportDto();

        var upperReservationName = reservation.getNameReservation().toUpperCase();

        if (startsWithLatinCyrillic(upperReservationName)) {
          var transliteratedNameOfRes = transliterate(upperReservationName);
          reservationReportDto.setFirstCharacterNameOfReservation(
              transliteratedNameOfRes.substring(0, 1));
          reservationReportDto.setNameOfReservation(transliteratedNameOfRes);
        } else {
          reservationReportDto.setFirstCharacterNameOfReservation(
              upperReservationName.substring(0, 1));
          reservationReportDto.setNameOfReservation(upperReservationName);
        }

        reservationReportDtos.add(reservationReportDto);
      }
    }
    return reservationReportDtos;
  }

  private static boolean startsWithLatinCyrillic(String input) {
    final var latinPattern = Pattern.compile("^[A-Za-z]+");

    Matcher matcher = latinPattern.matcher(input);
    return matcher.find();
  }

  private static String transliterate(String message) {
    char[] abcCyr = {' ', 'А', 'Б', 'В', 'Г', 'Д', 'Ѓ', 'Е', 'Ж', 'З', 'Ѕ', 'И', 'Ј', 'К', 'Л', 'Љ',
        'М', 'Н', 'Њ', 'О', 'П', 'Р', 'С', 'Т', 'Ќ', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Џ', 'Ш'};
    String[] abcLat = {" ", "A", "B", "V", "G", "D", "Gj", "E", "Zh", "Z", "Dz", "I", "J", "K", "L",
        "Lj", "M", "N", "Nj", "O", "P", "R", "S", "T", "KJ", "U", "F", "H", "C", "Ch", "Dzh", "Sh"};
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      for (int x = 0; x < abcCyr.length; x++) {
        if (message.charAt(i) == abcCyr[x]) {
          builder.append(abcLat[x]);
        }
      }
    }
    return builder.toString();
  }
}
