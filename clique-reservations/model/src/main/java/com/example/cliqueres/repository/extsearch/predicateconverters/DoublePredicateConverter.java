package com.example.cliqueres.repository.extsearch.predicateconverters;

import jakarta.annotation.Priority;
import jakarta.persistence.criteria.Expression;
import org.springframework.stereotype.Component;

@Priority(35)
@Component
public class DoublePredicateConverter extends NumberPredicateConverter<Double> {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(Double.class) || attribute.getJavaType()
        .equals(double.class);
  }

  @Override
  protected Class<Double> getNumberClass() {
    return Double.class;
  }

  @Override
  protected Double convertFromString(final String input) {
    return Double.parseDouble(input);
  }

}
