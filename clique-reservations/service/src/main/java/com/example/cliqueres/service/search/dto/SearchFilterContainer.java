package com.example.cliqueres.service.search.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SearchFilterContainer implements SearchTerm {

  @Builder.Default
  private SearchTermConjunction conjunction = SearchTermConjunction.AND;
  @Builder.Default
  private List<? extends SearchTerm> filters = new ArrayList<>();

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("conjunction", conjunction)
        .append("filters", filters)
        .toString();
  }
}
