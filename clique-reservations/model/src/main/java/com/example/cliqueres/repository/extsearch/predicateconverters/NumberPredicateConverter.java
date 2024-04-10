package com.example.cliqueres.repository.extsearch.predicateconverters;


import com.example.cliqueres.repository.extsearch.QueryFilter;
import com.example.cliqueres.repository.extsearch.QueryFilterComparator;
import com.example.cliqueres.repository.extsearch.exception.InvalidQueryRequestException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.Collection;

public abstract class NumberPredicateConverter<T extends Number> implements PredicateConverter {

  @Override
  public Predicate convert(final CriteriaBuilder cb, final QueryFilter filter,
      final Object compareValue, final Expression<?> attribute) {
    try {
      if (filter.getComparator() == QueryFilterComparator.IN
          && compareValue instanceof Collection<?> collection) {
        return attribute.in(collection);
      }
      Class<T> numberClass = getNumberClass();
      T convertedCompareValue = convertToNumberObject(compareValue, numberClass);
      switch (filter.getComparator()) {
        case EQUALS -> {
          return cb.equal(attribute, convertedCompareValue);
        }
        case GT -> {
          return cb.gt(attribute.as(numberClass), convertedCompareValue);
        }
        case LT -> {
          return cb.lt(attribute.as(numberClass), convertedCompareValue);
        }
        case GTE -> {
          return cb.ge(attribute.as(numberClass), convertedCompareValue);
        }
        case LTE -> {
          return cb.le(attribute.as(numberClass), convertedCompareValue);
        }
        case STARTS_WITH -> {
          return cb.like(attribute.as(String.class), convertedCompareValue + "%");
        }
        case ENDS_WITH -> {
          return cb.like(attribute.as(String.class), "%" + convertedCompareValue);
        }
        case LIKE -> {
          return cb.like(attribute.as(String.class), "%" + convertedCompareValue + "%");
        }
        default -> throw new InvalidQueryRequestException(
            String.format("Got an invalid operator (%s) for path (%s)!", filter.getComparator(),
                filter.getPath()));
      }
    } catch (NumberFormatException e) {
      throw new InvalidQueryRequestException(
          String.format("Got a non numeric value for the float path (%s)!", filter.getPath()));
    }
  }

  private T convertToNumberObject(final Object compareValue, final Class<T> numberClass) {
    if (compareValue.getClass().isAssignableFrom(numberClass)) {
      return numberClass.cast(compareValue);
    }
    return convertFromString(compareValue.toString());
  }

  protected abstract Class<T> getNumberClass();

  protected abstract T convertFromString(final String input);

}
