package com.example.cliqueres.repository.extsearch.predicateconverters;

import com.example.cliqueres.repository.extsearch.QueryFilter;
import com.example.cliqueres.repository.extsearch.exception.InvalidQueryRequestException;
import jakarta.annotation.Priority;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

@Priority(25)
@Component
public class LocalDatePredicateConverter implements PredicateConverter {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(LocalDate.class);
  }

  @Override
  public Predicate convert(final CriteriaBuilder cb, final QueryFilter filter,
      final Object compareValue, final Expression<?> attribute) {
    LocalDate convertedCompareValue;
    if (filter.getValue() instanceof String) {
      try {
        convertedCompareValue = LocalDate.from(Instant.parse(compareValue.toString()));
      } catch (DateTimeParseException e) {
        throw new InvalidQueryRequestException(
            String.format("Got an invalid datetime value for the path (%s)!", filter.getPath()));
      }
    } else {
      convertedCompareValue = (LocalDate) compareValue;
    }
    switch (filter.getComparator()) {
      case EQUALS -> {
        return cb.equal(attribute, convertedCompareValue);
      }
      case GT -> {
        return cb.greaterThan(attribute.as(LocalDate.class), convertedCompareValue);
      }
      case LT -> {
        return cb.lessThan(attribute.as(LocalDate.class), convertedCompareValue);
      }
      case GTE -> {
        return cb.greaterThanOrEqualTo(attribute.as(LocalDate.class), convertedCompareValue);
      }
      case LTE -> {
        return cb.lessThanOrEqualTo(attribute.as(LocalDate.class), convertedCompareValue);
      }
      default -> throw new InvalidQueryRequestException(
          String.format("Got an invalid operator (%s) for path (%s)!", filter.getComparator(),
              filter.getPath()));
    }
  }

}
