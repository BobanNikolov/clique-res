package com.example.cliqueres.service.reservation;

import com.example.cliqueres.domain.enums.ReservationType;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import com.example.cliqueres.service.reservation.dto.ReservationPersistCommand;

import java.util.List;

public interface ReservationService {
  ReservationDto save(ReservationPersistCommand reservation);
  ReservationDto update(ReservationPersistCommand reservation);

  ReservationDto getById(Long id);

  boolean delete(Long id);

  List<ReservationDto> getAllByReservationTypes(List<String> reservationTypes);

}
