package com.example.cliqueres.repository;

import com.example.cliqueres.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Long countReservationsByNameReservationEqualsIgnoreCaseAndEventId(String nameOfReservation, Long eventId);
}
