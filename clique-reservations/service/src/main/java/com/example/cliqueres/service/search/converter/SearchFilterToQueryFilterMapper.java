package com.example.cliqueres.service.search.converter;


import static com.example.cliqueres.service.search.converter.SearchFilterComparatorToQueryFilterComparatorMapper.toQueryFilterComparator;

import com.example.cliqueres.repository.extsearch.QueryFilter;
import com.example.cliqueres.service.search.dto.SearchFilter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class SearchFilterToQueryFilterMapper {

  public static QueryFilter toQueryFilter(final SearchFilter searchFilter) {
    final QueryFilter target = new QueryFilter();

    target.setValue(searchFilter.getValue());
    target.setPath(searchFilter.getPath());
    target.setComparator(toQueryFilterComparator(searchFilter.getComparator()));
    target.setExclude(searchFilter.isExclude());

    return target;
  }
}
