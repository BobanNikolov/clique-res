package com.example.cliqueres.service.search.converter;


import static com.example.cliqueres.service.search.converter.SearchFilterContainerToQueryFilterContainerMapper.toQueryFilterContainer;
import static com.example.cliqueres.service.search.converter.SearchFilterToQueryFilterMapper.toQueryFilter;

import com.example.cliqueres.repository.extsearch.QueryTerm;
import com.example.cliqueres.service.search.dto.SearchFilter;
import com.example.cliqueres.service.search.dto.SearchFilterContainer;
import com.example.cliqueres.service.search.dto.SearchTerm;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class SearchTermToQueryTermMapper {

  public static QueryTerm toQueryTerm(final SearchTerm searchTerm) {
    final QueryTerm target;

    if (searchTerm instanceof SearchFilter searchFilter) {
      target = toQueryFilter(searchFilter);
    } else if (searchTerm instanceof SearchFilterContainer searchFilterContainer) {
      target = toQueryFilterContainer(searchFilterContainer);
    } else {
      LOGGER.error("convert - unhandled SearchTerm type = {}", searchTerm.getClass().getName());
      target = null;
    }

    return target;
  }
}
