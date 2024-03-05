package com.example.cliqueres.service.reservation.dto;

import com.example.cliqueres.service.reservation.dto.validator.DuplicateNameOfReservationForSameEventValidation;
import com.example.cliqueres.service.validator.ModificationValidationGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DuplicateNameOfReservationForSameEventValidation(groups = {Default.class, ModificationValidationGroup.class})
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
