package com.example.cliqueres.service.search.gql;

import static com.fasterxml.jackson.annotation.Nulls.SKIP;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * SearchFilter DTO for search implementation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GqlSearchFilter implements SearchFilter {

  @Override
  public boolean isOrOperator() {
    return orOperator;
  }

  /**
   * Column filter item.
   */
  @Getter
  @Setter
  @NoArgsConstructor
  @SuperBuilder
  @AllArgsConstructor(staticName = "of")
  public static class GqlColumnFilter<T> implements ColumnFilter<T> {

    private String field;
    private @NotNull T value;
    @Builder.Default
    @JsonSetter(nulls = SKIP)
    private Boolean innerOrOperator = false;
    private Boolean exclude = false;
  }

  /**
   * String column filter.
   */
  @SuperBuilder
  @AllArgsConstructor
  public static class StringColumnFilter extends GqlColumnFilter<String> {

  }

  /**
   * Boolean column filter.
   */
  public static class BooleanColumnFilter extends GqlColumnFilter<Boolean> {

  }

  /**
   * Date range column filter.
   */
  public static class DateRangeColumnFilter extends GqlColumnFilter<DateRangeFilterValue> {

  }

  /**
   * Date time range column filter.
   */
  public static class DateTimeRangeColumnFilter extends GqlColumnFilter<DateTimeRangeFilterValue> {

  }

  /**
   * Number range column filter.
   */
  public static class NumberColumnFilter extends GqlColumnFilter<NumberFilterValue> {

  }

  /**
   * Character column filter.
   */
  public static class CharacterColumnFilter extends GqlColumnFilter<Character> {

  }

  /**
   * String list column filter.
   */
  public static class StringCollectionColumnFilter
      extends GqlColumnFilter<StringCollectionFilterValue> {

  }

  /**
   * Number list column filter.
   */
  public static class NumberCollectionColumnFilter
      extends GqlColumnFilter<NumberCollectionFilterValue> {

  }

  @Builder.Default
  private List<@NotNull StringColumnFilter> stringFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull BooleanColumnFilter> booleanFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull DateRangeColumnFilter> dateFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull DateTimeRangeColumnFilter> dateTimeFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull NumberColumnFilter> numberFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull CharacterColumnFilter> characterFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull StringCollectionColumnFilter> stringCollectionFilters = new ArrayList<>();
  @Builder.Default
  private List<@NotNull NumberCollectionColumnFilter> numberCollectionFilters = new ArrayList<>();
  private List<Sort> sort;
  private boolean orOperator = false;
  private int pageNum;
  private int pageSize;
  private boolean pageable = true;
}
