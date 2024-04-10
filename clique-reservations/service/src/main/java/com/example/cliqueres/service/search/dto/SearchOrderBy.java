package com.example.cliqueres.service.search.dto;

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
@EqualsAndHashCode
@Builder
public class SearchOrderBy {

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
