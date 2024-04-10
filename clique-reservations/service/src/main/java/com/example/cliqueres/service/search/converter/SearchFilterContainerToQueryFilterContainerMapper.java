package com.example.cliqueres.service.search.converter;

import com.example.cliqueres.repository.extsearch.QueryFilterContainer;
import com.example.cliqueres.service.search.dto.SearchFilterContainer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class SearchFilterContainerToQueryFilterContainerMapper {

  public static QueryFilterContainer toQueryFilterContainer(
      SearchFilterContainer searchFilterContainer) {
    final QueryFilterContainer target = new QueryFilterContainer();

    target.setConjunction(
        SearchTermConjunctionToQueryTermConjunctionMapper.toQueryTermConjunction(searchFilterContainer.getConjunction()));
    target.setFilters(searchFilterContainer.getFilters().stream()
        .map(SearchTermToQueryTermMapper::toQueryTerm)
        .toList());

    return target;
  }
}
