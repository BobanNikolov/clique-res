package com.example.cliqueres.service.event;

import com.example.cliqueres.service.event.dto.EventDto;
import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import org.springframework.data.domain.Page;

public interface EventSearchService {
  Page<EventDto> searchEvent(GqlSearchFilter request);
}
