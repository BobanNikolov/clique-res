package com.example.cliqueres.repository.extsearch;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {

  @NotNull
  private Integer pageNumber;
  @NotNull
  private Integer pageSize;
  private QueryTermConjunction conjunction = QueryTermConjunction.AND;
  private List<@Valid QueryTerm> filters;
  private List<@Valid QueryOrderBy> orderby;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("pageNumber", pageNumber)
        .append("pageSize", pageSize)
        .append("conjunction", conjunction)
        .append("filters", filters)
        .append("orderby", orderby)
        .toString();
  }
}
