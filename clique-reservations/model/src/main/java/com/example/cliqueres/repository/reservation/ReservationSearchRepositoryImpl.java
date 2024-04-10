package com.example.cliqueres.repository.reservation;

import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.repository.extsearch.AbstractExtendedSearchRepository;
import com.example.cliqueres.repository.extsearch.predicateconverters.PredicateConverter;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ReservationSearchRepositoryImpl extends
    AbstractExtendedSearchRepository<Reservation> implements
    ReservationSearchRepository {
protected ReservationSearchRepositoryImpl(EntityManager entityManager,
    List<PredicateConverter> converterList) {
    super(entityManager, converterList);
    }

@Override
public Class<Reservation> getEntityClass() {
    return Reservation.class;
  }

}
