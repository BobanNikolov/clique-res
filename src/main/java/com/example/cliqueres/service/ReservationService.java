package com.example.cliqueres.service;

import com.example.cliqueres.domain.Importance;
import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.domain.Type;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
  Reservation save(Reservation reservation);

  Reservation update(Reservation reservation);

  void deleteById(Long id);

  List<Reservation> findByDate(LocalDate date);

  List<Reservation> findByReservedBy(String reservedBy);

  List<Reservation> findByDateAndImportance(LocalDate date, Importance importance);

  List<Reservation> findByDateAndType(LocalDate date, Type type);
}
