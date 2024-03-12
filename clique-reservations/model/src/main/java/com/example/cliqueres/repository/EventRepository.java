package com.example.cliqueres.repository;

import com.example.cliqueres.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
  Long countAllByDateOfEventAndIdNot(LocalDate dateOfEvent, Long id);
}
