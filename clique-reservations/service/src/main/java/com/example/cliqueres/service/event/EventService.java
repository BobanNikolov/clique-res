package com.example.cliqueres.service.event;

import com.example.cliqueres.service.event.dto.EventDto;
import com.example.cliqueres.service.event.dto.EventPersistCommand;

public interface EventService {
  EventDto save(EventPersistCommand event);
  EventDto update(EventPersistCommand event);

  EventDto getById(Long id);

  boolean delete(Long id);
}
