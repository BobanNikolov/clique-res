package com.example.cliqueres.service.reservation.impl;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.repository.ReservationRepository;
import com.example.cliqueres.service.reservation.ReservationService;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import com.example.cliqueres.service.reservation.dto.ReservationPersistCommand;
import com.example.cliqueres.service.validator.ModificationValidationGroup;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
  private final ReservationRepository repository;
  private final Validator validator;
  private final ConversionService conversionService;

  @Override
  public ReservationDto save(ReservationPersistCommand reservation) {
    final var constrainViolations = validator.validate(reservation);
    if (!constrainViolations.isEmpty()) {
      throw new ConstraintViolationException("Reservation(SAVE) failed validation!", constrainViolations);
    }
    final var reservationToSave = convert(reservation);
    final var result = repository.saveAndFlush(reservationToSave);
    return conversionService.convert(result, ReservationDto.class);
  }

  @Override
  public ReservationDto update(ReservationPersistCommand reservation) {
    final var constrainViolations = validator.validate(reservation, ModificationValidationGroup.class);
    if (!constrainViolations.isEmpty()) {
      throw new ConstraintViolationException("Reservation(UPDATE) failed validation!", constrainViolations);
    }
    final var reservationToUpdate = repository.getReferenceById(reservation.getId());
    final var reservationWithUpdateInfo = convert(reservation);
    final var result = repository.saveAndFlush(merge(reservationToUpdate, reservationWithUpdateInfo));
    return conversionService.convert(result, ReservationDto.class);
  }

  @Override
  public ReservationDto getById(Long id) {
    return conversionService.convert(repository.getReferenceById(id), ReservationDto.class);
  }

  @Override
  public boolean delete(Long id) {
    repository.deleteAllByIdInBatch(List.of(id));
    return true;
  }

  private Reservation convert(ReservationPersistCommand reservationPersistCommand) {
    if (reservationPersistCommand == null) {
      return null;
    }
    final var reservation = new Reservation();
    reservation.setId(reservationPersistCommand.getId());
    reservation.setNameReservation(reservationPersistCommand.getNameReservation());
    reservation.setNumOfTables(reservationPersistCommand.getNumOfTables());
    reservation.setNumOfPeople(reservationPersistCommand.getNumOfPeople());
    reservation.setEvent(Event.builder().id(reservationPersistCommand.getEventId()).build());
    reservation.setCreatedBy(reservationPersistCommand.getCreatedBy());
    return reservation;
  }

  private Reservation merge(Reservation reservationToUpdate, Reservation reservation) {
    if (reservation == null) {
      return null;
    }
    reservationToUpdate.setId(reservation.getId());
    reservationToUpdate.setNameReservation(reservation.getNameReservation());
    reservationToUpdate.setNumOfTables(reservation.getNumOfTables());
    reservationToUpdate.setNumOfPeople(reservation.getNumOfPeople());
    reservationToUpdate.setEvent(reservation.getEvent());
    reservationToUpdate.setCreatedBy(reservation.getCreatedBy());

    return reservationToUpdate;
  }
}
