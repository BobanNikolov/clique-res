package com.example.cliqueres.service.search.gql;

/**
 * Interface for the Column Filter.
 *
 * @param <T> class
 */
public interface ColumnFilter<T> {

  String getField();

  T getValue();

  Boolean getInnerOrOperator();
}
