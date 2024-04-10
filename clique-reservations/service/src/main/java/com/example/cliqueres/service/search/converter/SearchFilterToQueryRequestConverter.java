package com.example.cliqueres.service.search.converter;


import static com.example.cliqueres.repository.extsearch.QueryTermConjunction.AND;
import static com.example.cliqueres.repository.extsearch.QueryTermConjunction.OR;
import static com.example.cliqueres.service.search.dto.SearchFilterComparator.EQUALS;
import static com.example.cliqueres.service.search.dto.SearchFilterComparator.GTE;
import static com.example.cliqueres.service.search.dto.SearchFilterComparator.IN;
import static com.example.cliqueres.service.search.dto.SearchFilterComparator.LIKE;
import static com.example.cliqueres.service.search.dto.SearchFilterComparator.LTE;
import static com.example.cliqueres.service.search.gql.Sort.Direction.DESC;

import com.example.cliqueres.repository.extsearch.QueryRequest;
import com.example.cliqueres.service.search.dto.SearchFilter;
import com.example.cliqueres.service.search.dto.SearchFilterComparator;
import com.example.cliqueres.service.search.dto.SearchFilterContainer;
import com.example.cliqueres.service.search.dto.SearchOrderBy;
import com.example.cliqueres.service.search.dto.SearchTerm;
import com.example.cliqueres.service.search.dto.SearchTermConjunction;
import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import com.example.cliqueres.service.search.gql.Sort;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class SearchFilterToQueryRequestConverter
    implements Converter<GqlSearchFilter, QueryRequest> {

  @Override
  public QueryRequest convert(final GqlSearchFilter source) {
    LOGGER.debug("convert - BEGIN : source = {}", source);
    final QueryRequest target = new QueryRequest();

    target.setPageNumber(source.getPageNum());
    target.setPageSize(source.getPageSize());
    LOGGER.debug("convert - after simple set : target = {}", target);

    target.setConjunction(source.isOrOperator() ? OR : AND);
    LOGGER.debug("convert - after setConjunction : target = {}", target);
    final var stringFilters = parseFilter(source.getStringFilters(), LIKE);
    final var booleanFilters = parseFilter(source.getBooleanFilters(), EQUALS);
    final var stringCollectionFilters =
        parseStringCollectionFilter(source.getStringCollectionFilters(), IN);
    final var characterFilters = parseFilter(source.getCharacterFilters(), EQUALS);
    final var dateFilters = parseDateRangeFilter(source.getDateFilters());
    final var dateTimeFilters = parseDateTimeRangeFilter(source.getDateTimeFilters());
    final var numberFilters = parseNumberRangeFilter(source.getNumberFilters());
    final var numberCollectionFilters =
        parseNumberCollectionFilter(source.getNumberCollectionFilters(), IN);
    final var queryTerms = Stream.of(stringFilters,
            booleanFilters,
            stringCollectionFilters,
            characterFilters,
            dateFilters,
            numberFilters,
            numberCollectionFilters,
            dateTimeFilters)
        .flatMap(Collection::stream)
        .map(SearchTermToQueryTermMapper::toQueryTerm)
        .toList();
    target.setFilters(queryTerms);
    LOGGER.debug("convert - after setFilters : target = {}", target);

    target.setOrderby(parseOrderString(source.getSort())
        .stream()
        .map(SearchOrderByToQueryOrderByMapper::toQueryOrderBy)
        .toList());

    LOGGER.debug("convert - END : target = {}", target);
    return target;
  }

  private List<? extends SearchTerm> parseDateTimeRangeFilter(
      List<GqlSearchFilter.DateTimeRangeColumnFilter> dateTimeFilters) {
    if (CollectionUtils.isEmpty(dateTimeFilters)) {
      return new ArrayList<>();
    }
    final var orFilters = dateTimeFilters.stream()
        .filter(GqlSearchFilter.GqlColumnFilter::getInnerOrOperator)
        .map(this::dateTimeRangeFilters)
        .map(filterCollection -> SearchFilterContainer.builder()
            .filters(filterCollection)
            .build())
        .toList();

    final var andFilters = dateTimeFilters.stream()
        .filter(filter -> !filter.getInnerOrOperator())
        .map(this::dateTimeRangeFilters)
        .map(filterCollection -> SearchFilterContainer.builder()
            .filters(filterCollection)
            .build())
        .toList();

    return getSearchFilterContainers(orFilters, andFilters);
  }

  private List<SearchFilter> dateTimeRangeFilters(
      GqlSearchFilter.DateTimeRangeColumnFilter filter) {
    if (filter.getValue().getDateFrom() != null && filter.getValue().getDateTo() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(GTE)
          .value(filter.getValue().getDateFrom())
          .exclude(filter.getExclude())
          .build(), SearchFilter.builder()
          .path(filter.getField())
          .comparator(LTE)
          .value(filter.getValue().getDateTo())
          .exclude(filter.getExclude())
          .build());
    } else if (filter.getValue().getDateFrom() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(GTE)
          .value(filter.getValue().getDateFrom())
          .exclude(filter.getExclude())
          .build());
    } else if (filter.getValue().getDateTo() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(LTE)
          .value(filter.getValue().getDateTo())
          .exclude(filter.getExclude())
          .build());
    }
    return Collections.emptyList();
  }

  private List<? extends SearchTerm> parseNumberRangeFilter(
      List<GqlSearchFilter.NumberColumnFilter> numberFilters) {
    if (CollectionUtils.isEmpty(numberFilters)) {
      return new ArrayList<>();
    }
    final var orFilters = numberFilters.stream()
        .filter(GqlSearchFilter.GqlColumnFilter::getInnerOrOperator)
        .map(this::numberRangeFilters)
        .map(filterCollection -> SearchFilterContainer.builder()
            .filters(filterCollection)
            .build())
        .toList();

    final var andFilters = numberFilters.stream()
        .filter(filter -> !filter.getInnerOrOperator())
        .map(this::numberRangeFilters)
        .map(filterCollection -> SearchFilterContainer.builder()
            .filters(filterCollection)
            .build())
        .toList();

    return getSearchFilterContainers(orFilters, andFilters);
  }

  private List<SearchFilter> numberRangeFilters(GqlSearchFilter.NumberColumnFilter filter) {
    if (filter.getValue().getValueFrom() != null && filter.getValue().getValueTo() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(GTE)
          .value(filter.getValue().getValueFrom().longValue())
          .exclude(filter.getExclude())
          .build(), SearchFilter.builder()
          .path(filter.getField())
          .comparator(LTE)
          .value(filter.getValue().getValueTo().longValue())
          .exclude(filter.getExclude())
          .build());
    } else if (filter.getValue().getValueFrom() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(GTE)
          .value(filter.getValue().getValueFrom().longValue())
          .exclude(filter.getExclude())
          .build());
    } else if (filter.getValue().getValueTo() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(LTE)
          .value(filter.getValue().getValueTo().longValue())
          .exclude(filter.getExclude())
          .build());
    }
    return Collections.emptyList();
  }

  private List<? extends SearchTerm> parseDateRangeFilter(
      List<GqlSearchFilter.DateRangeColumnFilter> rangeFilters) {
    if (CollectionUtils.isEmpty(rangeFilters)) {
      return new ArrayList<>();
    }
    final var orFilters = rangeFilters.stream()
        .filter(GqlSearchFilter.GqlColumnFilter::getInnerOrOperator)
        .map(this::dateRangeFilters)
        .map(filterCollection -> SearchFilterContainer.builder()
            .filters(filterCollection)
            .build())
        .toList();

    final var andFilters = rangeFilters.stream()
        .filter(filter -> !filter.getInnerOrOperator())
        .map(this::dateRangeFilters)
        .map(filterCollection -> SearchFilterContainer.builder()
            .filters(filterCollection)
            .build())
        .toList();

    return getSearchFilterContainers(orFilters, andFilters);
  }

  private List<SearchFilter> dateRangeFilters(GqlSearchFilter.DateRangeColumnFilter filter) {
    if (filter.getValue().getDateFrom() != null && filter.getValue().getDateTo() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(GTE)
          .value(filter.getValue().getDateFrom())
          .exclude(filter.getExclude())
          .build(), SearchFilter.builder()
          .path(filter.getField())
          .comparator(LTE)
          .value(filter.getValue().getDateTo())
          .exclude(filter.getExclude())
          .build());
    } else if (filter.getValue().getDateFrom() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(GTE)
          .value(filter.getValue().getDateFrom())
          .exclude(filter.getExclude())
          .build());
    } else if (filter.getValue().getDateTo() != null) {
      return List.of(SearchFilter.builder()
          .path(filter.getField())
          .comparator(LTE)
          .value(filter.getValue().getDateTo())
          .exclude(filter.getExclude())
          .build());
    }
    return Collections.emptyList();
  }

  private List<? extends SearchTerm> parseNumberCollectionFilter(
      final List<GqlSearchFilter.NumberCollectionColumnFilter> filters,
      final SearchFilterComparator comparator) {
    if (CollectionUtils.isEmpty(filters)) {
      return new ArrayList<>();
    }

    final var orFilters = filters.stream()
        .filter(GqlSearchFilter.GqlColumnFilter::getInnerOrOperator)
        .map(filter -> SearchFilter.builder()
            .path(filter.getField())
            .comparator(comparator)
            .value(filter.getValue().getNumberCollection())
            .exclude(filter.getExclude())
            .build())
        .toList();
    final var andFilters = filters.stream()
        .filter(filter -> !filter.getInnerOrOperator())
        .map(filter -> SearchFilter.builder()
            .path(filter.getField())
            .comparator(comparator)
            .value(filter.getValue().getNumberCollection())
            .exclude(filter.getExclude())
            .build())
        .toList();
    return getSearchFilterContainers(orFilters, andFilters);
  }

  private List<? extends SearchTerm> parseStringCollectionFilter(
      final List<GqlSearchFilter.StringCollectionColumnFilter> filters,
      final SearchFilterComparator comparator) {
    if (CollectionUtils.isEmpty(filters)) {
      return new ArrayList<>();
    }

    final var orFilters = filters.stream()
        .filter(GqlSearchFilter.GqlColumnFilter::getInnerOrOperator)
        .map(filter -> SearchFilter.builder()
            .path(filter.getField())
            .comparator(comparator)
            .value(filter.getValue().getStringCollection())
            .exclude(filter.getExclude())
            .build())
        .toList();
    final var andFilters = filters.stream()
        .filter(filter -> !filter.getInnerOrOperator())
        .map(filter -> SearchFilter.builder()
            .path(filter.getField())
            .comparator(comparator)
            .value(filter.getValue().getStringCollection())
            .exclude(filter.getExclude())
            .build())
        .toList();
    return getSearchFilterContainers(orFilters, andFilters);
  }

  private <F extends GqlSearchFilter.GqlColumnFilter> List<? extends SearchTerm> parseFilter(
      final List<F> filters, final SearchFilterComparator comparator) {
    if (CollectionUtils.isEmpty(filters)) {
      return new ArrayList<>();
    }

    final var orFilters = filters.stream()
        .filter(GqlSearchFilter.GqlColumnFilter::getInnerOrOperator)
        .map(filter -> SearchFilter.builder()
            .path(filter.getField())
            .comparator(comparator)
            .value(filter.getValue())
            .exclude(filter.getExclude())
            .build())
        .toList();
    final var andFilters = filters.stream()
        .filter(filter -> !filter.getInnerOrOperator())
        .map(filter -> SearchFilter.builder()
            .path(filter.getField())
            .comparator(comparator)
            .value(filter.getValue())
            .exclude(filter.getExclude())
            .build())
        .toList();
    return getSearchFilterContainers(orFilters, andFilters);
  }

  private static List<SearchOrderBy> parseOrderString(final List<Sort> orders) {
    if (CollectionUtils.isEmpty(orders)) {
      return new ArrayList<>();
    }
    return orders.stream()
        .map(order -> SearchOrderBy.builder()
            .path(order.getField())
            .desc(DESC == order.getDirection())
            .build())
        .toList();
  }

  private static List<? extends SearchTerm> getSearchFilterContainers(
      List<? extends SearchTerm> orFilters,
      List<? extends SearchTerm> andFilters) {
    SearchTerm orTerm;
    SearchTerm andTerm;
    if (orFilters.size() > 1) {
      orTerm = SearchFilterContainer.builder()
          .filters(orFilters)
          .conjunction(SearchTermConjunction.OR)
          .build();
    } else if (orFilters.size() == 1) {
      orTerm = orFilters.get(0);
    } else {
      orTerm = null;
    }
    if (andFilters.size() > 1) {
      andTerm = SearchFilterContainer.builder()
          .filters(andFilters)
          .conjunction(SearchTermConjunction.AND)
          .build();
    } else if (andFilters.size() == 1) {
      andTerm = andFilters.get(0);
    } else {
      andTerm = null;
    }

    return Stream.of(orTerm, andTerm)
        .filter(Objects::nonNull)
        .toList();
  }
}
