package com.example.cliqueres.service.search.gql;

import java.util.List;

/**
 * Interface for the SearchFilter.
 */
public interface SearchFilter {

  List<? extends ColumnFilter<String>> getStringFilters();

  List<? extends ColumnFilter<Boolean>> getBooleanFilters();

  List<? extends ColumnFilter<DateRangeFilterValue>> getDateFilters();

  List<? extends ColumnFilter<DateTimeRangeFilterValue>> getDateTimeFilters();

  List<? extends ColumnFilter<NumberFilterValue>> getNumberFilters();

  List<? extends ColumnFilter<Character>> getCharacterFilters();

  List<? extends ColumnFilter<StringCollectionFilterValue>> getStringCollectionFilters();

  List<? extends ColumnFilter<NumberCollectionFilterValue>> getNumberCollectionFilters();

  boolean isOrOperator();

  List<Sort> getSort();

  int getPageNum();

  int getPageSize();

  boolean isPageable();
}
