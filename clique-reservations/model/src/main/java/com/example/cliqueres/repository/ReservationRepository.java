package com.example.cliqueres.repository;

import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.domain.enums.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Long countReservationsByNameReservationEqualsIgnoreCaseAndEventId(String nameOfReservation, Long eventId);

  List<Reservation> findAllByReservationTypeIn(List<ReservationType> reservationTypes);
}
