package com.example.cliqueres.endpoint.reservation.converter;

import com.example.cliqueres.endpoint.reservation.dto.ReservationOut;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationDtoToOutConverter implements Converter<ReservationDto, ReservationOut> {
  @Override
  public ReservationOut convert(ReservationDto source) {
    if (source == null) {
      return null;
    }
    final ReservationOut reservationOut = new ReservationOut();
    reservationOut.setId(source.getId());
    reservationOut.setNameReservation(source.getNameReservation());
    reservationOut.setNumOfPeople(source.getNumOfPeople());
    reservationOut.setNumOfTables(source.getNumOfTables());
    reservationOut.setCreatedBy(source.getCreatedBy());
    reservationOut.setEventId(source.getEventId());
    return reservationOut;
  }
}
