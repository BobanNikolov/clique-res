package com.example.cliqueres.service.user.impl;

import com.example.cliqueres.domain.UserAccount;
import com.example.cliqueres.repository.useraccount.UserAccountRepository;
import com.example.cliqueres.service.user.UserAccountService;
import com.example.cliqueres.service.user.dto.UserAccountDto;
import com.example.cliqueres.service.user.dto.UserAccountPersistCommand;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

  private final UserAccountRepository repository;
  private final Validator validator;
  private final ConversionService conversionService;

  @Override
  public UserAccountDto save(UserAccountPersistCommand user) {
    final var constrainViolations = validator.validate(user);
    if (!constrainViolations.isEmpty()) {
      throw new ConstraintViolationException("UserAccount(SAVE) failed validation!", constrainViolations);
    }
    final var userAccountToSave = convert(user);
    final var result = repository.saveAndFlush(userAccountToSave);
    return conversionService.convert(result, UserAccountDto.class);
  }

  @Override
  public Long countUsers() {
    return repository.count();
  }

  public UserDetailsService userDetailsService() {
    return username -> repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  private UserAccount convert(UserAccountPersistCommand source) {
    if (source == null) {
      return null;
    }
    return UserAccount.builder()
        .username(source.getUsername())
        .email(source.getEmail())
        .password(source.getPassword())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .displayName(source.getFirstName() + " " + source.getLastName())
        .role(source.getRole())
        .build();
  }
}
