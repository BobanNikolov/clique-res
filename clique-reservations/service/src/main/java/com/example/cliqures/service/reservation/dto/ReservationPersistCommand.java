package com.example.cliqures.service.reservation.dto;

import com.example.cliqures.service.validator.ModificationValidationGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationPersistCommand {
  @NotNull(groups = ModificationValidationGroup.class)
  private Long id;
  @NotNull
  private String nameReservation;
  private Long numOfPeople;
  @NotNull
  private Long numOfTables;
  @NotNull
  private Long createdBy;
  @NotNull
  private Long event;
}
