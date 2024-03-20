package com.example.cliqueres.service.event.impl;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.repository.EventRepository;
import com.example.cliqueres.service.event.EventService;
import com.example.cliqueres.service.event.dto.EventDto;
import com.example.cliqueres.service.event.dto.EventPersistCommand;
import com.example.cliqueres.service.validator.ModificationValidationGroup;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.cliqueres.service.util.DateAdjuster.fromStringToLocalDate;
import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
  private final EventRepository repository;
  private final Validator validator;
  private final ConversionService conversionService;

  @Override
  public EventDto save(EventPersistCommand event) {
    final var constrainViolations = validator.validate(event);
    if (!constrainViolations.isEmpty()) {
      throw new ConstraintViolationException("Event(SAVE) failed validation!", constrainViolations);
    }
    final var reservationToSave = convert(event);
    final var result = repository.saveAndFlush(reservationToSave);
    return conversionService.convert(result, EventDto.class);
  }

  @Override
  public EventDto update(EventPersistCommand event) {
    final var constrainViolations = validator.validate(event, ModificationValidationGroup.class);
    if (!constrainViolations.isEmpty()) {
      throw new ConstraintViolationException("Event(UPDATE) failed validation!", constrainViolations);
    }
    final var eventToUpdate = repository.findById(event.getId());
    if (eventToUpdate.isEmpty()) {
      throw new EntityNotFoundException(format("There is no event with id : %d", event.getId()));
    }
    final var eventWithUpdateInfo = convert(event);
    final var result = repository.saveAndFlush(merge(eventToUpdate.get(), eventWithUpdateInfo));
    return conversionService.convert(result, EventDto.class);
  }

  @Override
  public EventDto getById(Long id) {
    return conversionService.convert(repository.getReferenceById(id), EventDto.class);
  }

  @Override
  public boolean delete(Long id) {
    repository.deleteAllByIdInBatch(List.of(id));
    return true;
  }

  private Event convert(EventPersistCommand eventPersistCommand) {
    if (eventPersistCommand == null) {
      return null;
    }
    final var event = new Event();
    event.setId(eventPersistCommand.getId());
    event.setNameOfEvent(eventPersistCommand.getNameOfEvent());
    event.setDateOfEvent(fromStringToLocalDate(eventPersistCommand.getDateOfEvent()));
    return event;
  }

  private Event merge(Event eventToUpdate, Event event) {
    if (event == null) {
      return null;
    }
    eventToUpdate.setId(event.getId());
    eventToUpdate.setNameOfEvent(event.getNameOfEvent());
    eventToUpdate.setDateOfEvent(event.getDateOfEvent());

    return eventToUpdate;
  }
}
