package com.example.cliqueres.endpoint.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationOut {
  private Long id;
  private String nameReservation;
  private Long numOfPeople;
  private Long numOfTables;
  private Long createdBy;
  private Long event;
}
