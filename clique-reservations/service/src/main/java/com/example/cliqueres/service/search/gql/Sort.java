package com.example.cliqueres.service.search.gql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sort filter item.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Sort {

  /**
   * Enumeration for the direction of the sort.
   */
  public enum Direction {
    DESC, ASC;
  }

  private String field;
  private Direction direction = Direction.ASC;
}
