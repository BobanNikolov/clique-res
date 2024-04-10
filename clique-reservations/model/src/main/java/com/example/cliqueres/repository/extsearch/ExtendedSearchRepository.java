package com.example.cliqueres.repository.extsearch;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.function.Function;
import org.springframework.data.domain.Page;

public interface ExtendedSearchRepository<E> {

  Page<E> query(@Valid @NotNull QueryRequest request);

  Page<E> query(@Valid @NotNull QueryRequest request, String entityGraph);

  <T> Page<T> query(@Valid @NotNull QueryRequest request, @NotNull Function<E, T> converter);

  <T> Page<T> query(@Valid @NotNull QueryRequest request, @NotNull Function<E, T> converter,
      String entityGraph);

  Page<E> query(@NotNull Class<E> entityClass, @Valid @NotNull QueryRequest request);

  Page<E> query(@NotNull Class<E> entityClass, @Valid @NotNull QueryRequest request,
      String entityGraph);

  <T> Page<T> query(@NotNull Class<E> entityClass, @Valid @NotNull QueryRequest request,
      @NotNull Function<E, T> converter);

  @SuppressWarnings("java:S2583")
  <T> Page<T> query(Class<E> entityClass, QueryRequest request, Function<E, T> converter,
      String entityGraph);

  long count(@NotNull Class<E> entityClass, @Valid @NotNull QueryRequest request);
}
