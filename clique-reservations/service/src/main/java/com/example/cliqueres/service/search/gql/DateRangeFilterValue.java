package com.example.cliqueres.service.search.gql;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Value for date column filters.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRangeFilterValue implements Serializable {
  LocalDate dateFrom;
  LocalDate dateTo;
}
