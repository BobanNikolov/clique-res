package com.example.cliqueres.service.reservation.impl;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.domain.enums.ReservationType;
import com.example.cliqueres.repository.reservation.ReservationRepository;
import com.example.cliqueres.repository.useraccount.UserAccountRepository;
import com.example.cliqueres.service.reservation.ReservationService;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
import com.example.cliqueres.service.reservation.dto.ReservationPersistCommand;
import com.example.cliqueres.service.validator.ModificationValidationGroup;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
  private final ReservationRepository repository;
  private final UserAccountRepository userAccountRepository;
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
    final var reservationToUpdate = repository.findById(reservation.getId());
    if (reservationToUpdate.isEmpty()) {
      throw new EntityNotFoundException(format("There is no reservation with id : %d", reservation.getId()));
    }
    final var reservationWithUpdateInfo = convert(reservation);
    final var result = repository.saveAndFlush(merge(reservationToUpdate.get(), reservationWithUpdateInfo));
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

  @Override
  public List<ReservationDto> getAllByReservationTypes(List<String> reservationTypes) {
    var values = Arrays.stream(ReservationType.values()).map(ReservationType::name).collect(Collectors.toSet());
    if (!values.containsAll(reservationTypes)) {
      throw new ValidationException("Reservation types values are not correct!");
    }
    final var reservationTypesAsEnums = reservationTypes.stream().map(ReservationType::valueOf).toList();
    final var result = repository.findAllByReservationTypeIn(reservationTypesAsEnums);
    return result.stream()
        .map(it -> conversionService.convert(it, ReservationDto.class))
        .filter(Objects::nonNull)
        .toList();
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
    final var createdBy = userAccountRepository.findById(reservationPersistCommand.getCreatedBy());
    if (createdBy.isEmpty()) {
      throw new EntityNotFoundException(format("There is no user with id : %d", reservationPersistCommand.getCreatedBy()));
    }
    reservation.setCreatedBy(createdBy.get());
    reservation.setReservationType(reservationPersistCommand.getReservationType());
    reservation.setPriceOfReservation(reservationPersistCommand.getPriceOfReservation());
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
    reservationToUpdate.setReservationType(reservation.getReservationType());
    reservationToUpdate.setPriceOfReservation(reservation.getPriceOfReservation());

    return reservationToUpdate;
  }
}
