package com.example.cliqueres.repository.reservation;

import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.domain.enums.ReservationType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>,
    ReservationSearchRepository {

  Long countReservationsByNameReservationEqualsIgnoreCaseAndEventId(String nameOfReservation,
      Long eventId);

  List<Reservation> findAllByReservationTypeIn(List<ReservationType> reservationTypes);
}
