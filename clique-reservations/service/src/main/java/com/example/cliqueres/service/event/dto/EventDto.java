package com.example.cliqueres.service.event.dto;

import com.example.cliqueres.service.reservation.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDto {
  private Long id;
  private String nameOfEvent;
  private LocalDate dateOfEvent;
  private List<ReservationDto> reservations;
}
