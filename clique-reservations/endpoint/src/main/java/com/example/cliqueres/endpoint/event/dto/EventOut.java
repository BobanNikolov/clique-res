package com.example.cliqueres.endpoint.event.dto;

import com.example.cliqueres.endpoint.reservation.dto.ReservationOut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventOut {
  private Long id;
  private String nameOfEvent;
  private String dateOfEvent;
  private List<ReservationOut> reservations;
}
