package com.example.cliqueres.service.security;

import com.example.cliqueres.service.security.dto.JwtAuthenticationResponse;
import com.example.cliqueres.service.security.dto.SignInRequest;
import com.example.cliqueres.service.security.dto.SignUpRequest;

public interface AuthenticationService {
  JwtAuthenticationResponse signUp(SignUpRequest request);

  JwtAuthenticationResponse signIn(SignInRequest request);
}
