package com.example.cliqueres.security;

import com.example.cliqueres.domain.enums.Role;
import com.example.cliqueres.service.user.UserAccountService;
import com.example.cliqueres.service.user.dto.UserAccountPersistCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

  private final UserAccountService userAccountService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {

    if (userAccountService.countUsers() == 0) {

      UserAccountPersistCommand admin = UserAccountPersistCommand
          .builder()
          .firstName("admin")
          .lastName("admin")
          .email("admin@admin.com")
          .username("admin")
          .password(passwordEncoder.encode("password"))
          .role(Role.ROLE_ADMIN)
          .build();

      userAccountService.save(admin);
      LOGGER.debug("created ADMIN user - {}", admin);
    }
  }

}