package com.example.cliqueres.repository.extsearch;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QueryFilterContainer implements QueryTerm {

  private QueryTermConjunction conjunction = QueryTermConjunction.AND;
  private List<QueryTerm> filters = new ArrayList<>();

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("conjunction", conjunction)
        .append("filters", filters)
        .toString();
  }
}
