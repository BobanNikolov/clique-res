package com.example.cliqueres.endpoint.user.dto;

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
public class UserAccountOut {
  private Long id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String displayName;
}
