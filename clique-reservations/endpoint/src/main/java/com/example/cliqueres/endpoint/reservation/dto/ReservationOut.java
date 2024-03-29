package com.example.cliqueres.endpoint.reservation.dto;

import com.example.cliqueres.domain.enums.ReservationType;
import com.example.cliqueres.endpoint.user.dto.UserAccountOut;
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
  private UserAccountOut createdBy;
  private Long eventId;
  private ReservationType reservationType;
  private Long priceOfReservation;
}
