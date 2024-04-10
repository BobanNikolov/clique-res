package com.example.cliqueres.service.search.gql;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Value for number column filters.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NumberFilterValue implements Serializable {
  BigDecimal valueFrom;
  BigDecimal valueTo;
}
