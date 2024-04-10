package com.example.cliqueres.endpoint.security;

import com.example.cliqueres.service.security.AuthenticationService;
import com.example.cliqueres.service.security.dto.JwtAuthenticationResponse;
import com.example.cliqueres.service.security.dto.SignInRequest;
import com.example.cliqueres.service.security.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
    return authenticationService.signUp(request);
  }

  @PostMapping("/signin")
  public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
    return authenticationService.signIn(request);
  }
}
