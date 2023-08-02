package com.example.cliqueres.service.impl;

import com.example.cliqueres.domain.User;
import com.example.cliqueres.domain.enums.Importance;
import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.domain.enums.Type;
import com.example.cliqueres.repository.ReservationRepository;
import com.example.cliqueres.service.ReservationService;
import java.time.LocalDate;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
  private final ReservationRepository repository;

  @Override
  @Transactional
  public Reservation save(Reservation reservation) {
    return repository.save(reservation);
  }

  @Override
  @Transactional
  public Reservation update(Reservation reservation) {
    return repository.save(reservation);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<Reservation> findByDate(LocalDate date) {
    return repository.findAllByReservedForDate(date);
  }

  @Override
  public List<Reservation> findByReservedBy(User user) {
    return repository.findAllByUserEquals(user);
  }

  @Override
  public List<Reservation> findByDateAndImportance(LocalDate date, Importance importance) {
    return repository.findAllByReservedForDateAndImportance(date, importance);
  }

  @Override
  public List<Reservation> findByDateAndType(LocalDate date, Type type) {
    return repository.findAllByReservedForDateAndType(date, type);
  }
}
