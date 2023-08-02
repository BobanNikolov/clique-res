package com.example.cliqueres.service;

import com.example.cliqueres.domain.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    Event save(Event event);

    Event update(Event event);

    void deleteById(Long id);

    Event findByDate(LocalDate date);

    List<Event> findAll();
}
