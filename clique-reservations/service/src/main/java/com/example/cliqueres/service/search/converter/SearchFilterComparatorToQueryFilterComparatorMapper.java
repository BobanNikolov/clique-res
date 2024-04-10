package com.example.cliqueres.service.search.converter;


import com.example.cliqueres.repository.extsearch.QueryFilterComparator;
import com.example.cliqueres.service.search.dto.SearchFilterComparator;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SearchFilterComparatorToQueryFilterComparatorMapper {

  public static QueryFilterComparator toQueryFilterComparator(
      final SearchFilterComparator searchFilterComparator) {
    return switch (searchFilterComparator) {
      case EQUALS -> QueryFilterComparator.EQUALS;
      case LIKE -> QueryFilterComparator.LIKE;
      case STARTS_WITH -> QueryFilterComparator.STARTS_WITH;
      case ENDS_WITH -> QueryFilterComparator.ENDS_WITH;
      case GT -> QueryFilterComparator.GT;
      case GTE -> QueryFilterComparator.GTE;
      case LT -> QueryFilterComparator.LT;
      case LTE -> QueryFilterComparator.LTE;
      case IN -> QueryFilterComparator.IN;
    };
  }
}
