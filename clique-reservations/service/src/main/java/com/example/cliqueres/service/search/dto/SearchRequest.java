package com.example.cliqueres.service.search.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

  @NotNull
  private Integer pageNumber;

  @NotNull
  private Integer pageSize;

  private SearchTermConjunction conjunction = SearchTermConjunction.AND;

  private String filters;

  private String orderby;

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
