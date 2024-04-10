package com.example.cliqueres.repository.extsearch;

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
@EqualsAndHashCode
public class QueryOrderBy {

  private String path;
  private boolean desc;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("path", path)
        .append("desc", desc)
        .toString();
  }
}
