package com.example.cliqueres.repository.event;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.repository.extsearch.AbstractExtendedSearchRepository;
import com.example.cliqueres.repository.extsearch.predicateconverters.PredicateConverter;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EventSearchRepositoryImpl extends AbstractExtendedSearchRepository<Event> implements
    EventSearchRepository {
  protected EventSearchRepositoryImpl(EntityManager entityManager,
      List<PredicateConverter> converterList) {
    super(entityManager, converterList);
  }

  @Override
  public Class<Event> getEntityClass() {
    return Event.class;
  }
}
