package com.example.cliqueres.service.event.impl;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.repository.event.EventRepository;
import com.example.cliqueres.repository.extsearch.QueryRequest;
import com.example.cliqueres.service.event.EventSearchService;
import com.example.cliqueres.service.event.dto.EventDto;
import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventSearchServiceImpl implements EventSearchService {
  private final EventRepository repository;
  private final ConversionService conversionService;

  @Override
  public Page<EventDto> searchEvent(GqlSearchFilter searchRequest) {
    final QueryRequest request = conversionService.convert(searchRequest, QueryRequest.class);
    Page<Event> searched = repository.query(request);

    Page<EventDto> result = new PageImpl<>(
        searched.stream()
            .map(dto -> conversionService.convert(dto, EventDto.class))
            .filter(Objects::nonNull)
            .toList(),
        searched.getPageable(), searched.getTotalElements());

    return result;
  }
}
