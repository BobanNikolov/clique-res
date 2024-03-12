package com.example.cliqueres.service.event.converter;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.service.event.dto.EventDto;
import com.example.cliqueres.service.reservation.converter.ReservationToReservationDtoConverter;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EventToEventDtoConverter implements Converter<Event, EventDto> {

  private final ReservationToReservationDtoConverter toReservationDtoConverter;

  @Override
  public EventDto convert(Event source) {
    if (source == null) {
      return null;
    }

    var eventDto = new EventDto();
    eventDto.setId(source.getId());
    eventDto.setNameOfEvent(source.getNameOfEvent());
    eventDto.setDateOfEvent(source.getDateOfEvent());

    var reservations = new ArrayList<ReservationDto>();
    if (source.getReservations() != null) {
      for (var reservation : source.getReservations()) {
        reservations.add(toReservationDtoConverter.convert(reservation));
      }
    }
    eventDto.setReservations(reservations);

    return eventDto;
  }
}
