package com.example.cliqueres.endpoint.reservation.converter;

import com.example.cliqueres.endpoint.reservation.dto.ReservationOut;
import com.example.cliqueres.endpoint.user.converter.UserAccountDtoToOutConverter;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationDtoToOutConverter implements Converter<ReservationDto, ReservationOut> {
  private final UserAccountDtoToOutConverter userAccountDtoToOutConverter;
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
    reservationOut.setCreatedBy(userAccountDtoToOutConverter.convert(source.getCreatedBy()));
    reservationOut.setEventId(source.getEventId());
    reservationOut.setReservationType(source.getReservationType());
    reservationOut.setPriceOfReservation(source.getPriceOfReservation());
    return reservationOut;
  }
}
