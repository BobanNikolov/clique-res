package com.example.cliqueres.service.reservation.converter;

import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationToReservationDtoConverter implements Converter<Reservation, ReservationDto> {

  @Override
  public ReservationDto convert(Reservation source) {
    if (source == null) {
      return null;
    }

    final ReservationDto reservationDto = new ReservationDto();
    reservationDto.setId(source.getId());
    reservationDto.setNameReservation(source.getNameReservation());
    reservationDto.setNumOfPeople(source.getNumOfPeople());
    reservationDto.setNumOfTables(source.getNumOfTables());
    reservationDto.setCreatedBy(source.getCreatedBy());
    reservationDto.setEventId(source.getEvent() != null ? source.getEvent().getId() : null);
    reservationDto.setReservationType(source.getReservationType());
    reservationDto.setPriceOfReservation(source.getPriceOfReservation());
    return reservationDto;
  }
}
