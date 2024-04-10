package com.example.cliqueres.service.search.converter;


import com.example.cliqueres.repository.extsearch.QueryTermConjunction;
import com.example.cliqueres.service.search.dto.SearchTermConjunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SearchTermConjunctionToQueryTermConjunctionMapper {

  public static QueryTermConjunction toQueryTermConjunction(
      SearchTermConjunction searchTermConjunction) {
      if (searchTermConjunction == null) {
          return QueryTermConjunction.AND;
      }

    final QueryTermConjunction target;

    target = switch (searchTermConjunction) {
      case OR -> QueryTermConjunction.OR;
      case AND -> QueryTermConjunction.AND;
    };

    return target;
  }
}
