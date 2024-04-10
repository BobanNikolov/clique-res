package com.example.cliqueres.service.security.impl;

import com.example.cliqueres.domain.UserAccount;
import com.example.cliqueres.repository.useraccount.UserAccountRepository;
import com.example.cliqueres.service.security.AuthenticationService;
import com.example.cliqueres.service.security.JwtService;
import com.example.cliqueres.service.security.dto.JwtAuthenticationResponse;
import com.example.cliqueres.service.security.dto.SignInRequest;
import com.example.cliqueres.service.security.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.cliqueres.domain.enums.Role.ROLE_HOST;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserAccountRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public JwtAuthenticationResponse signUp(SignUpRequest request) {
    var user = UserAccount
        .builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .username(request.getUsername())
        .displayName(request.getFirstName() + " " + request.getLastName())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole() == null ? ROLE_HOST : request.getRole())
        .build();

    var savedUser = userRepository.saveAndFlush(user);
    var jwt = jwtService.generateToken(savedUser);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }


  @Override
  public JwtAuthenticationResponse signIn(SignInRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    var user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));
    var jwt = jwtService.generateToken(user);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }

}
