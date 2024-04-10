package com.example.cliqueres.endpoint.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PageResponse<T> implements Serializable {

  private List<T> elements;
  private Integer currentPage;
  private Integer currentPageSize;
  private Integer totalPages;
  private Long totalElements;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("elements", elements)
        .append("currentPage", currentPage)
        .append("currentPageSize", currentPageSize)
        .append("totalPages", totalPages)
        .append("totalElements", totalElements)
        .toString();
  }

  public static <T> GenericPageResponseBuilder<T> builder() {
    return new GenericPageResponseBuilder<>();
  }

  public static final class GenericPageResponseBuilder<T> {
    private List<T> elements;
    private Integer currentPage;
    private Integer currentPageSize;
    private Integer totalPages;
    private Long totalElements;

    public GenericPageResponseBuilder<T> elements(final List<T> elements) {
      this.elements = elements;
      return this;
    }

    public GenericPageResponseBuilder<T> currentPage(final Integer currentPage) {
      this.currentPage = currentPage;
      return this;
    }

    public GenericPageResponseBuilder<T> currentPageSize(final Integer currentPageSize) {
      this.currentPageSize = currentPageSize;
      return this;
    }

    public GenericPageResponseBuilder<T> totalPages(final Integer totalPages) {
      this.totalPages = totalPages;
      return this;
    }

    public GenericPageResponseBuilder<T> totalElements(final Long totalElements) {
      this.totalElements = totalElements;
      return this;
    }

    public GenericPageResponseBuilder<T> pageMetadata(final Page<?> page) {
      currentPage = page.getNumber();
      currentPageSize = page.getSize();
      totalPages = page.getTotalPages();
      totalElements = page.getTotalElements();
      return this;
    }

    public PageResponse<T> build() {
      return new PageResponse<>(elements,
          currentPage, currentPageSize,
          totalPages, totalElements);
    }
  }
}
