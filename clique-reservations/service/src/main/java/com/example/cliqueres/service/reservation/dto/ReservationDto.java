package com.example.cliqueres.service.reservation.dto;

import com.example.cliqueres.domain.enums.ReservationType;
import com.example.cliqueres.service.user.dto.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto {
  private Long id;
  private String nameReservation;
  private Long numOfPeople;
  private Long numOfTables;
  private UserAccountDto createdBy;
  private Long eventId;
  private ReservationType reservationType;
  private Long priceOfReservation;
}
