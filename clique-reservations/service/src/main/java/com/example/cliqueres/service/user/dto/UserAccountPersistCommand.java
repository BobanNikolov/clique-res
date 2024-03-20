package com.example.cliqueres.service.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountPersistCommand {
  private String username;
  @NotNull
  private String email;
  @NotNull
  private String password;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
}
