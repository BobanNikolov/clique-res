package com.example.cliqueres.service.reservation.dto.validator;

import com.example.cliqueres.repository.ReservationRepository;
import com.example.cliqueres.service.reservation.dto.ReservationPersistCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DuplicateNameOfReservationForSameEventValidator
    implements ConstraintValidator<DuplicateNameOfReservationForSameEventValidation, ReservationPersistCommand> {
  private final ReservationRepository repository;

  @Override
  public boolean isValid(final ReservationPersistCommand value, final ConstraintValidatorContext context) {
    return repository.countReservationsByNameReservationEqualsIgnoreCaseAndEventId(value.getNameReservation(),
        value.getEventId()) == 0;
  }
}
