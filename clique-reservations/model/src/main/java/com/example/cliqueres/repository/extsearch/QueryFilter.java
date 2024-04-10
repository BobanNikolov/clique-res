package com.example.cliqueres.repository.extsearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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
public class QueryFilter implements QueryTerm {

  @NotNull
  private String path;
  private Object value;
  @NotNull
  private QueryFilterComparator comparator;
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
