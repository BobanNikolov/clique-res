package com.example.cliqueres.service.search.converter;


import com.example.cliqueres.repository.extsearch.QueryOrderBy;
import com.example.cliqueres.service.search.dto.SearchOrderBy;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SearchOrderByToQueryOrderByMapper {

  public static QueryOrderBy toQueryOrderBy(SearchOrderBy source) {
    final QueryOrderBy target = new QueryOrderBy();

    target.setPath(source.getPath());
    target.setDesc(source.isDesc());

    return target;
  }
}
