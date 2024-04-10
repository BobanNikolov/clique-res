package com.example.cliqueres.repository.extsearch.predicateconverters;

import jakarta.annotation.Priority;
import jakarta.persistence.criteria.Expression;
import org.springframework.stereotype.Component;

@Priority(45)
@Component
public class IntegerPredicateConverter extends NumberPredicateConverter<Integer> {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(Integer.class) || attribute.getJavaType()
        .equals(int.class);
  }

  @Override
  protected Class<Integer> getNumberClass() {
    return Integer.class;
  }

  @Override
  protected Integer convertFromString(final String input) {
    return Integer.parseInt(input);
  }

}
