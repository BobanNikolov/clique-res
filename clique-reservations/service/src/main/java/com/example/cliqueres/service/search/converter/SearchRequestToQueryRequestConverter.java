package com.example.cliqueres.service.search.converter;

import com.example.cliqueres.repository.extsearch.QueryRequest;
import com.example.cliqueres.repository.extsearch.exception.BadRequestException;
import com.example.cliqueres.service.search.dto.SearchFilter;
import com.example.cliqueres.service.search.dto.SearchFilterComparator;
import com.example.cliqueres.service.search.dto.SearchOrderBy;
import com.example.cliqueres.service.search.dto.SearchRequest;
import com.example.cliqueres.service.search.dto.SearchTerm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SearchRequestToQueryRequestConverter implements
    Converter<SearchRequest, QueryRequest> {

  @Override
  public QueryRequest convert(final SearchRequest source) {
    LOGGER.debug("convert - BEGIN : source = {}", source);
    final QueryRequest target = new QueryRequest();

    target.setPageNumber(source.getPageNumber());
    target.setPageSize(source.getPageSize());
    LOGGER.debug("convert - after simple set : target = {}", target);

    target.setConjunction(
        SearchTermConjunctionToQueryTermConjunctionMapper.toQueryTermConjunction(source.getConjunction()));
    LOGGER.debug("convert - after setConjunction : target = {}", target);

    target.setFilters(parseFilterString(source.getFilters())
        .stream()
        .map(SearchTermToQueryTermMapper::toQueryTerm)
        .toList());
    LOGGER.debug("convert - after setFilters : target = {}", target);

    target.setOrderby(parseOrderString(source.getOrderby())
        .stream()
        .map(SearchOrderByToQueryOrderByMapper::toQueryOrderBy)
        .toList());

    LOGGER.debug("convert - END : target = {}", target);
    return target;
  }

  private static final String PART_SEPARATOR = ";";
  private static final String SUBPART_SEPARATOR = ":";
  private static final String NOT_INDICATOR_PREFIX = "!";
  private static final String NULL_MARKER = "$NULL$";

  private static List<? extends SearchTerm> parseFilterString(final String filter) {
    if (StringUtils.isBlank(filter)) {
      return new ArrayList<>();
    }
    final String[] filterParts = StringUtils.split(filter, PART_SEPARATOR);
    return Arrays
        .stream(filterParts)
        .map(part -> {
          final String[] subparts = StringUtils.splitPreserveAllTokens(part, SUBPART_SEPARATOR);
          if (subparts.length < 3) {
            throw new BadRequestException();
          }
          StringBuilder value = new StringBuilder(subparts[2]);
          if (subparts.length > 3) {
            for (int i = 3; i < subparts.length; i++) {
              value.append(SUBPART_SEPARATOR).append(subparts[i]);
            }
          }
          return new SearchFilter(
              StringUtils.trim(subparts[0]),
              StringUtils.equals(value.toString(), NULL_MARKER) ? null : value.toString(),
              SearchFilterComparator.valueOf(StringUtils.upperCase(
                  StringUtils.removeStart(StringUtils.trim(subparts[1]), NOT_INDICATOR_PREFIX))),
              StringUtils.startsWith(subparts[1], NOT_INDICATOR_PREFIX)
          );
        }).toList();
  }

  private static List<SearchOrderBy> parseOrderString(final String order) {
    if (StringUtils.isBlank(order)) {
      return new ArrayList<>();
    }
    final String[] orderParts = StringUtils.split(order, PART_SEPARATOR);
    return Arrays.stream(orderParts).map(part -> {
      if (part.contains(SUBPART_SEPARATOR)) {
        final String[] subparts = StringUtils.split(part, SUBPART_SEPARATOR);
        return new SearchOrderBy(StringUtils.trim(subparts[0]),
            StringUtils.equalsIgnoreCase(StringUtils.trim(subparts[1]), "desc"));
      }
      return new SearchOrderBy(StringUtils.trim(part), false);
    }).toList();
  }
}
