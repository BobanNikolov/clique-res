package com.example.cliqueres.service.reservation;

import com.example.cliqueres.service.reservation.dto.ReservationDto;
import com.example.cliqueres.service.reservation.dto.ReservationPersistCommand;

public interface ReservationService {
  ReservationDto save(ReservationPersistCommand reservation);
  ReservationDto update(ReservationPersistCommand reservation);

  ReservationDto getById(Long id);

  boolean delete(Long id);

}
