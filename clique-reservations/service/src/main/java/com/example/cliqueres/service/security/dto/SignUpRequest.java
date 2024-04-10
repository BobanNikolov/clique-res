package com.example.cliqueres.service.security.dto;

import com.example.cliqueres.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;
  private Role role;
}