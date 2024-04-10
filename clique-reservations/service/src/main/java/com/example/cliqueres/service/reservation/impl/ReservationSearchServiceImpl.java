package com.example.cliqueres.service.reservation.impl;

import com.example.cliqueres.domain.Reservation;
import com.example.cliqueres.repository.extsearch.QueryRequest;
import com.example.cliqueres.repository.reservation.ReservationRepository;
import com.example.cliqueres.service.reservation.ReservationSearchService;
import com.example.cliqueres.service.reservation.dto.ReservationDto;
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
public class ReservationSearchServiceImpl implements ReservationSearchService {
  private final ReservationRepository repository;
  private final ConversionService conversionService;

  @Override
  public Page<ReservationDto> searchReservation(GqlSearchFilter searchRequest) {
    final QueryRequest request = conversionService.convert(searchRequest, QueryRequest.class);
    Page<Reservation> searched = repository.query(request);

    Page<ReservationDto> result = new PageImpl<>(
        searched.stream()
            .map(dto -> conversionService.convert(dto, ReservationDto.class))
            .filter(Objects::nonNull)
            .toList(),
        searched.getPageable(), searched.getTotalElements());

    return result;
  }
}
