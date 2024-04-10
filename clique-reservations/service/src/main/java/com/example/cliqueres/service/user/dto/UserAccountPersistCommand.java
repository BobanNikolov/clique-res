package com.example.cliqueres.service.user.dto;

import com.example.cliqueres.domain.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
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
  @NotNull
  private Role role;
}
