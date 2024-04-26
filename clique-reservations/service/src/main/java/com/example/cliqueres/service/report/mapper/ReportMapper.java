package com.example.cliqueres.service.report.mapper;

import com.example.cliqueres.service.report.dto.ReservationReportDto;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    char[] abcCyr = {'А', 'Б', 'В', 'Г', 'Д', 'Ѓ', 'Е', 'Ж', 'З', 'Ѕ', 'И', 'Ј', 'К', 'Л', 'Љ', 'М',
        'Н', 'Њ', 'О', 'П', 'Р', 'С', 'Т', 'Ќ', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Џ', 'Ш'};
    final var existingCharsFromAbc = reservationReportDtos.stream()
        .map(ReservationReportDto::getFirstCharacterNameOfReservation)
        .collect(Collectors.toSet());
    var deltaOfAbc = Stream.of(abcCyr).map(Object::toString).collect(Collectors.toSet());
    deltaOfAbc.removeAll(existingCharsFromAbc);

    deltaOfAbc.forEach(d -> {
      var reservation = new ReservationReportDto();

      reservation.setFirstCharacterNameOfReservation(d);
      reservation.setNameOfReservation("");

      reservationReportDtos.add(reservation);
    });

    return reservationReportDtos;
  }

  private static boolean startsWithLatinCyrillic(String input) {
    final var latinPattern = Pattern.compile("^[A-Za-z]+");

    Matcher matcher = latinPattern.matcher(input);
    return matcher.find();
  }

  private static String transliterate(String message) {
    Map<String, Character> translitMap = new HashMap<>();
    char[] abcCyr = {' ', 'А', 'Б', 'В', 'Г', 'Д', 'Ѓ', 'Е', 'Ж', 'З', 'Ѕ', 'И', 'Ј', 'К', 'Л', 'Љ',
        'М', 'Н', 'Њ', 'О', 'П', 'Р', 'С', 'Т', 'Ќ', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Џ', 'Ш'};
    String[] abcLat = {" ", "A", "B", "V", "G", "D", "GJ", "E", "ZH", "Z", "DZ", "I", "J", "K", "L",
        "LJ", "M", "N", "NJ", "O", "P", "R", "S", "T", "KJ", "U", "F", "H", "C", "CH", "DZH", "SH"};

    for (int i = 0; i < abcCyr.length; i++) {
      translitMap.put(abcLat[i], abcCyr[i]);
    }

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      boolean matchFound = false;

      // Check for multicharacter transliterations first
      for (String key : Arrays.asList("GJ", "ZH", "DZ", "LJ", "NJ", "KJ", "CH", "SH", "DZH")) {
        if (i + key.length() <= message.length() && message.substring(i, i + key.length())
            .equals(key)) {
          builder.append(translitMap.get(key));
          i += key.length() - 1; // Move index past the current match
          matchFound = true;
          break;
        }
      }

      // If no multicharacter match, look for single character match
      if (!matchFound) {
        String singleChar = String.valueOf(message.charAt(i));
        if (translitMap.containsKey(singleChar)) {
          builder.append(translitMap.get(singleChar));
        }
      }
    }
    return builder.toString();
  }

}
