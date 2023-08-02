package com.example.cliqueres.repository;

import com.example.cliqueres.domain.User;
import com.example.cliqueres.domain.enums.Importance;
import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.domain.enums.Type;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  List<Reservation> findAllByReservedForDate(LocalDate date);

  List<Reservation> findAllByUserEquals(User user);

  List<Reservation> findAllByReservedForDateAndImportance(LocalDate date, Importance importance);

  List<Reservation> findAllByReservedForDateAndType(LocalDate date, Type type);
}
