package com.example.cliqueres.repository.extsearch.predicateconverters;

import jakarta.annotation.Priority;
import jakarta.persistence.criteria.Expression;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Priority(25)
@Component
public class BigDecimalPredicateConverter extends NumberPredicateConverter<BigDecimal> {

  @Override
  public boolean canHandle(final Expression<?> attribute) {
    return attribute.getJavaType().isAssignableFrom(BigDecimal.class);
  }

  @Override
  protected Class<BigDecimal> getNumberClass() {
    return BigDecimal.class;
  }

  @Override
  protected BigDecimal convertFromString(final String input) {
    return new BigDecimal(input);
  }

}
