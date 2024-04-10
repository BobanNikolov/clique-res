package com.example.cliqueres.service.search.gql;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Value for collection column filters.
 */
@Getter
@Setter
@NoArgsConstructor
public class StringCollectionFilterValue {
  List<String> stringCollection;
}
