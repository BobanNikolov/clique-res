package com.example.cliqueres.service.event.dto;

import com.example.cliqueres.service.event.dto.validator.CheckIfEventOnSameDayValidation;
import com.example.cliqueres.service.validator.ModificationValidationGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@CheckIfEventOnSameDayValidation(groups = {Default.class, ModificationValidationGroup.class})
public class EventPersistCommand {
  @NotNull(groups = ModificationValidationGroup.class)
  private Long id;
  @NotNull
  private String nameOfEvent;
  @NotNull
  private String dateOfEvent;
}
