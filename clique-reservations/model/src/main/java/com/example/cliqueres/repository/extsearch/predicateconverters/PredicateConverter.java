package com.example.cliqueres.repository.extsearch.predicateconverters;

import com.example.cliqueres.repository.extsearch.QueryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public interface PredicateConverter {

  boolean canHandle(final Expression<?> attribute);

  Predicate convert(final CriteriaBuilder cb, final QueryFilter filter, final Object compareValue,
      final Expression<?> attribute);

}
