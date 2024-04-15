package com.example.cliqueres.repository.extsearch;

import static java.util.Collections.emptyList;

import com.example.cliqueres.repository.extsearch.exception.InvalidQueryRequestException;
import com.example.cliqueres.repository.extsearch.predicateconverters.PredicateConverter;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;


@Slf4j
public abstract class AbstractExtendedSearchRepository<E> implements ExtendedSearchRepository<E> {

  private final EntityManager entityManager;
  private final List<PredicateConverter> converterList;

  int maxResultCount = 200;
  int defaultResultCount = 20;
  String pathSeparator = ".";

  public abstract Class<E> getEntityClass();

  protected AbstractExtendedSearchRepository(final EntityManager entityManager,
                                             final List<PredicateConverter> converterList) {
    this.entityManager = entityManager;
    this.converterList = converterList;
  }

  @Override
  public Page<E> query(@Valid @NotNull final QueryRequest request) {
    return query(getEntityClass(), request, x -> x, null);
  }

  @Override
  public Page<E> query(@Valid @NotNull final QueryRequest request, final String entityGraph) {
    return query(getEntityClass(), request, x -> x, entityGraph);
  }

  @Override
  public <T> Page<T> query(@Valid @NotNull final QueryRequest request,
                           @NotNull final Function<E, T> converter) {
    return query(getEntityClass(), request, converter, null);
  }

  @Override
  public <T> Page<T> query(@Valid @NotNull final QueryRequest request,
                           @NotNull final Function<E, T> converter, final String entityGraph) {
    return query(getEntityClass(), request, converter, entityGraph);
  }

  @Override
  public Page<E> query(@NotNull final Class<E> entityClass,
                       @Valid @NotNull final QueryRequest request) {
    return query(entityClass, request, x -> x, null);
  }

  @Override
  public Page<E> query(@NotNull final Class<E> entityClass,
                       @Valid @NotNull final QueryRequest request, final String entityGraph) {
    return query(entityClass, request, x -> x, entityGraph);
  }

  @Override
  public <T> Page<T> query(@NotNull final Class<E> entityClass,
                           @Valid @NotNull final QueryRequest request,
                           @NotNull final Function<E, T> converter) {
    return query(entityClass, request, converter, null);
  }

  // we suppress this warning since in the future it may be possible to set the amount to null
  @Override
  @SuppressWarnings("java:S2583")
  public <T> Page<T> query(final Class<E> entityClass, final QueryRequest request,
                           final Function<E, T> converter, final String entityGraph) {
    final long available = count(entityClass, request);
    if (request.getPageSize() <= 0 || available == 0) {
      return new PageImpl<>(emptyList());
    }
    final Map<String, Join<?, ?>> joinMap = new HashMap<>();
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<E> criteriaQuery = cb.createQuery(entityClass);
    final Root<E> from = criteriaQuery.from(entityClass);
    criteriaQuery.where(
        createFilterPredicates(cb, from, request.getFilters(), request.getConjunction(), joinMap));
    criteriaQuery.orderBy(createOrderByPredicates(cb, from, request.getOrderby(), joinMap));
    final TypedQuery<E> q = entityManager.createQuery(criteriaQuery);
    if (StringUtils.isNotBlank(entityGraph)) {
      try {
        EntityGraph<?> graph = entityManager.getEntityGraph(entityGraph);
        q.setHint("javax.persistence.loadgraph", graph);
      } catch (IllegalArgumentException e) {
        LOGGER.warn(
            "Unable to locate the requested entity graph '{}'. Executing the query non the less.",
            request);
      }
    }
    q.setFirstResult(request.getPageNumber() * request.getPageSize());
    q.setMaxResults(
        request.getPageSize() != null ? Math.min(request.getPageSize(), maxResultCount) :
            defaultResultCount);
    @SuppressWarnings("java:S6204") // don't want to return an unmodifiable list
    final List<T> results = q.getResultList().stream().map(converter).toList();
    final Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
    return new PageImpl<>(results, pageable, available);
  }

  @Override
  public long count(@NotNull final Class<E> entityClass,
                    @Valid @NotNull final QueryRequest request) {
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    final Root<E> from = countQuery.from(entityClass);
    final Map<String, Join<?, ?>> joinMap = new HashMap<>();
    countQuery.select(cb.count(from));
    countQuery.where(
        createFilterPredicates(cb, from, request.getFilters(), request.getConjunction(), joinMap));
    return entityManager.createQuery(countQuery).getSingleResult();
  }

  protected Predicate createFilterPredicates(final CriteriaBuilder cb, final Root<E> from,
                                             final List<QueryTerm> terms,
                                             final QueryTermConjunction conjunction,
                                             final Map<String, Join<?, ?>> joinMap) {
    return createFilterPredicatesFrom(cb, from, terms, conjunction, joinMap);
  }

  protected Predicate createFilterPredicatesFrom(final CriteriaBuilder cb, final From<?, E> from,
                                                 final List<QueryTerm> terms,
                                                 final QueryTermConjunction conjunction,
                                                 final Map<String, Join<?, ?>> joinMap) {
    if (terms == null || terms.isEmpty()) {
      return cb.and();
    }
    final List<Predicate> predicates = new ArrayList<>();
    for (final QueryTerm term : terms) {
      if (term instanceof QueryFilterContainer container) {
        predicates.add(
            createFilterPredicatesFrom(cb, from, container.getFilters(), container.getConjunction(),
                joinMap));
      } else if (term instanceof QueryFilter filter) {
        final Expression<?> attribute =
            convertPathToExpression(filter.getPath(), from, JoinType.LEFT, joinMap);
        final Predicate predicate = translatePredicate(cb, filter, attribute);
        predicates.add(filter.isExclude() ? cb.not(predicate) : predicate);
      } else {
        LOGGER.warn("Got an unknown query term ...");
      }
    }
    return conjunction == QueryTermConjunction.AND ?
        cb.and(predicates.toArray(new Predicate[0])) :
        cb.or(predicates.toArray(new Predicate[0]));
  }

  private Predicate translatePredicate(final CriteriaBuilder cb, final QueryFilter filter,
                                       final Expression<?> attribute) {
    final Object compareValue = adaptCompareValue(attribute, filter.getValue());
    if (compareValue == null) {
      return cb.isNull(attribute);
    }
    for (PredicateConverter converter : converterList) {
      if (!converter.canHandle(attribute)) {
        continue;
      }
      return converter.convert(cb, filter, compareValue, attribute);
    }
    throw new InvalidQueryRequestException(String.format(
        "Got a filter that addresses a data type with no special treatment! Path: %s, Class: %s, Primitive: %b",
        filter.getPath(), attribute.getJavaType().getName(),
        attribute.getJavaType().isPrimitive()));
  }

  protected List<Order> createOrderByPredicates(CriteriaBuilder cb, Root<E> from,
                                                List<QueryOrderBy> orderby,
                                                Map<String, Join<?, ?>> joinMap) {
    return createOrderByPredicatesFrom(cb, from, orderby, joinMap);
  }

  protected List<Order> createOrderByPredicatesFrom(CriteriaBuilder cb, From<?, E> from,
                                                    List<QueryOrderBy> orderby,
                                                    Map<String, Join<?, ?>> joinMap) {
    if (CollectionUtils.isEmpty(orderby)) {
      return new ArrayList<>();
    }
    return orderby.stream()
        .filter(o -> StringUtils.isNotEmpty(o.getPath()))
        .map(o -> {
          final Expression<?> orderBase =
              convertPathToExpression(o.getPath(), from, JoinType.LEFT, joinMap);
          if (orderBase.getJavaType().isAssignableFrom(String.class)) {
            final var stringExpression = (Expression<String>) orderBase;
            if (o.isDesc()) {
              return cb.desc(
                  cb.function("clique_res.naturalsort", String.class,
                      cb.lower(stringExpression)));
            } else {
              return cb.asc(
                  cb.function("clique_res.naturalsort", String.class,
                      cb.lower(stringExpression)));
            }
          }
          return o.isDesc() ? cb.desc(orderBase) : cb.asc(orderBase);
        })
        .toList();
  }

  private Expression<?> convertPathToExpression(final String pathString, final From<?, ?> from,
                                                final JoinType joinType,
                                                final Map<String, Join<?, ?>> joinMap) {
    return getExpressionOfJoinBase("", pathString, from, joinType, joinMap);
  }

  private Expression<?> getExpressionOfJoinBase(final String pathSoFar, final String remainingPath,
                                                final From<?, ?> joinBase, final JoinType joinType,
                                                final Map<String, Join<?, ?>> joinMap) {
    if (!StringUtils.contains(remainingPath, pathSeparator)) {
      return joinBase.get(remainingPath.trim());
    }
    final String subPath = joinNotEmptyStrings(pathSeparator, pathSoFar,
        StringUtils.substringBefore(remainingPath, pathSeparator));
    final Join<?, ?> fittingJoin = joinMap.computeIfAbsent(subPath, key -> joinBase.join(
        StringUtils.defaultIfBlank(StringUtils.substringAfterLast(key, "."), key), joinType));
    return getExpressionOfJoinBase(subPath,
        StringUtils.substringAfter(remainingPath, pathSeparator), fittingJoin, joinType, joinMap);
  }

  /**
   * This method can be overwritten in the repositories if it is required to adapt the compare value
   * depending on the target property. E.g. if the target has a specific type or a specific
   * annotation.
   *
   * @param literal the compare value object, that can be adapted
   * @param target  the target column on which the compare will be performed
   */
  protected Object adaptCompareValue(final Expression<?> target, final Object literal) {
    return literal;
  }

  /**
   * Returns a stream of all none static fields of the current entity
   * <p>
   * !!!! this code uses reflection and won't work if quarkus is used in native way !!!!
   */

  private String joinNotEmptyStrings(String delimiter, final String... inputValues) {
    if (inputValues == null) {
      return "";
    }
    if (delimiter == null) {
      delimiter = "";
    }
    return Stream.of(inputValues).filter(StringUtils::isNotEmpty)
        .collect(Collectors.joining(delimiter));
  }

}
