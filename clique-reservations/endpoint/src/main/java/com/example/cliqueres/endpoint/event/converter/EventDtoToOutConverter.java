package com.example.cliqueres.endpoint.event.converter;

import com.example.cliqueres.endpoint.reservation.converter.ReservationDtoToOutConverter;
import com.example.cliqueres.endpoint.event.dto.EventOut;
import com.example.cliqueres.endpoint.reservation.dto.ReservationOut;
import com.example.cliqueres.service.event.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.example.cliqueres.service.util.DateAdjuster.fromLocalDateToString;


@Component
@RequiredArgsConstructor
public class EventDtoToOutConverter implements Converter<EventDto, EventOut> {

  private final ReservationDtoToOutConverter reservationDtoToOutConverter;
  @Override
  public EventOut convert(EventDto source) {
    if (source == null) {
      return null;
    }
    var event = new EventOut();
    event.setId(source.getId());
    event.setNameOfEvent(source.getNameOfEvent());
    event.setDateOfEvent(fromLocalDateToString(source.getDateOfEvent()));
    var reservations = new ArrayList<ReservationOut>();
    for(var reservation : source.getReservations()) {
      reservations.add(reservationDtoToOutConverter.convert(reservation));
    }
    event.setReservations(reservations);

    return event;
  }
}
