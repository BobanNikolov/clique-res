package com.example.cliqueres.repository.extsearch.predicateconverters;

import static com.example.cliqueres.repository.extsearch.QueryFilterComparator.EQUALS;

import com.example.cliqueres.repository.extsearch.QueryFilter;
import com.example.cliqueres.repository.extsearch.exception.InvalidQueryRequestException;
import jakarta.annotation.Priority;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Priority(45)
@Component
public class BooleanPredicateConverter implements PredicateConverter {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(Boolean.class) || attribute.getJavaType()
        .equals(boolean.class);
  }

  @Override
  public Predicate convert(final CriteriaBuilder cb, final QueryFilter filter,
      final Object compareValue, final Expression<?> attribute) {
    if (filter.getComparator() != EQUALS) {
      throw new InvalidQueryRequestException(
          String.format("Got an invalid operator (%s) for path (%s)!", filter.getComparator(),
              filter.getPath()));
    }
    if (compareValue instanceof String) {
      return cb.equal(attribute.as(Boolean.class), Boolean.valueOf(compareValue.toString()));
    }
    return cb.equal(attribute, compareValue);
  }
}
