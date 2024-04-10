package com.example.cliqueres.endpoint.event.controller;


import com.example.cliqueres.endpoint.dto.PageResponse;
import com.example.cliqueres.endpoint.event.dto.EventOut;
import com.example.cliqueres.service.event.EventSearchService;
import com.example.cliqueres.service.event.EventService;
import com.example.cliqueres.service.event.dto.EventPersistCommand;
import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;
  private final EventSearchService eventSearchService;
  private final ConversionService conversionService;

  @PostMapping("/save")
  public ResponseEntity<EventOut> save(@RequestBody EventPersistCommand event) {
    final var result = this.eventService.save(event);
    final var convertedResult = this.conversionService.convert(result, EventOut.class);
    return ResponseEntity.ok(convertedResult);
  }

  @PutMapping("/update")
  public ResponseEntity<EventOut> update(@RequestBody EventPersistCommand event) {
    final var result = this.eventService.update(event);
    final var convertedResult = this.conversionService.convert(result, EventOut.class);
    return ResponseEntity.ok(convertedResult);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Long id) {
    var deleted = this.eventService.delete(id);
    return ResponseEntity.ok(deleted);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventOut> getById(@PathVariable(name = "id") Long id) {
    var result = this.eventService.getById(id);
    final var convertedResult = this.conversionService.convert(result, EventOut.class);
    return ResponseEntity.ok(convertedResult);
  }

  @PostMapping("/search")
  public PageResponse<EventOut> search(@RequestBody GqlSearchFilter filter) {
    final var result = eventSearchService.searchEvent(filter);
    final var searchedEvents = result.getContent().stream()
        .map(dto -> conversionService.convert(dto, EventOut.class))
        .toList();
    final var response = PageResponse
        .<EventOut>builder()
        .pageMetadata(result)
        .elements(searchedEvents)
        .build();

    return response;
  }
}

