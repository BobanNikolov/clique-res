package com.example.cliqueres.service.reservation;

import com.example.cliqueres.service.reservation.dto.ReservationDto;
import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import org.springframework.data.domain.Page;

public interface ReservationSearchService {
  Page<ReservationDto> searchReservation(GqlSearchFilter request);
}
