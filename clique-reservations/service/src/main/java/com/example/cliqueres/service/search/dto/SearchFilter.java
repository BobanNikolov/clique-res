package com.example.cliqueres.service.search.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class SearchFilter implements SearchTerm {

  @NotNull
  private String path;
  private Object value;
  @NotNull
  private SearchFilterComparator comparator;
  private boolean exclude;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("path", path)
        .append("value", value)
        .append("comparator", comparator)
        .append("exclude", exclude)
        .toString();
  }
}
