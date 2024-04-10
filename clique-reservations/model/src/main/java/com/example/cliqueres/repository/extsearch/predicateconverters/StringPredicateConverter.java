package com.example.cliqueres.repository.extsearch.predicateconverters;

import com.example.cliqueres.repository.extsearch.QueryFilter;
import com.example.cliqueres.repository.extsearch.exception.InvalidQueryRequestException;
import jakarta.annotation.Priority;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Priority(50)
@Component
public class StringPredicateConverter implements PredicateConverter {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(String.class) ||
        attribute.getJavaType().isEnum();
  }

  @Override
  public Predicate convert(final CriteriaBuilder cb, final QueryFilter filter, Object compareValue,
      final Expression<?> attribute) {
    boolean attributeIsEnum = attribute.getJavaType().isEnum();
    if (attributeIsEnum && !(compareValue instanceof Collection<?>) &&
        !(compareValue instanceof String[])) {
      compareValue = compareValue.toString();
    }
    switch (filter.getComparator()) {
      case IN -> {
        if (compareValue instanceof Collection<?> collection) {
          if (attributeIsEnum) {
            collection = collection.stream().map(Object::toString).collect(Collectors.toSet());
          }
          return attribute.as(String.class).in(collection);
        } else if (compareValue instanceof String[] array) {
          if (attributeIsEnum) {
            array = (String[]) Arrays.stream(array).map(Object::toString).toArray();
          }
          return attribute.as(String.class).in(Arrays.asList(array));
        }
      }
      case EQUALS -> {
        return cb.equal(attribute.as(String.class), compareValue);
      }
      case STARTS_WITH -> {
        return cb.like(cb.lower(attribute.as(String.class)),
            String.format("%s%%", compareValue).toLowerCase());
      }
      case ENDS_WITH -> {
        return cb.like(cb.lower(attribute.as(String.class)),
            String.format("%%%s", compareValue).toLowerCase());
      }
      case LIKE -> {
        return cb.like(cb.lower(attribute.as(String.class)),
            String.format("%%%s%%", compareValue).toLowerCase());
      }
      default -> throw new InvalidQueryRequestException(
          String.format("Got an invalid operator (%s) for path (%s)!", filter.getComparator(),
              filter.getPath()));
    }
    throw new InvalidQueryRequestException(
        String.format("Got an invalid operator (%s) for path (%s)!", filter.getComparator(),
            filter.getPath()));
  }

}
