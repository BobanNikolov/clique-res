package com.example.cliqueres.repository.extsearch.predicateconverters;

import jakarta.annotation.Priority;
import jakarta.persistence.criteria.Expression;
import org.springframework.stereotype.Component;

@Priority(40)
@Component
public class LongPredicateConverter extends NumberPredicateConverter<Long> {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(Long.class) || attribute.getJavaType()
        .equals(long.class);
  }

  @Override
  protected Class<Long> getNumberClass() {
    return Long.class;
  }

  @Override
  protected Long convertFromString(final String input) {
    return Long.parseLong(input);
  }

}
