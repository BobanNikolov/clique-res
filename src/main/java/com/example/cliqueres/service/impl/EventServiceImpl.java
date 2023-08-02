package com.example.cliqueres.service.impl;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.repository.EventRepository;
import com.example.cliqueres.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;

    @Override
    @Transactional
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    @Transactional
    public Event update(Event event) {
        return repository.save(event);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Event findByDate(LocalDate date) {
        return repository.findByDateEquals(date);
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }
}
