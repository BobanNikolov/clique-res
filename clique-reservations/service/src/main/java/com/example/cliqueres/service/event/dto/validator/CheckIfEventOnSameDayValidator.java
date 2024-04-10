package com.example.cliqueres.service.event.dto.validator;

import com.example.cliqueres.repository.event.EventRepository;
import com.example.cliqueres.service.event.dto.EventPersistCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static com.example.cliqueres.service.util.DateAdjuster.fromStringToLocalDate;

@RequiredArgsConstructor
public class CheckIfEventOnSameDayValidator
    implements ConstraintValidator<CheckIfEventOnSameDayValidation, EventPersistCommand> {
  private final EventRepository repository;

  @Override
  public boolean isValid(final EventPersistCommand value, final ConstraintValidatorContext context) {
    return repository.countAllByDateOfEventAndIdNot(fromStringToLocalDate(value.getDateOfEvent()), value.getId()) == 0;
  }
}
