package com.example.cliqueres.service.user;

import com.example.cliqueres.service.user.dto.UserAccountDto;
import com.example.cliqueres.service.user.dto.UserAccountPersistCommand;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService {
  UserAccountDto save(UserAccountPersistCommand user);

  Long countUsers();

  UserDetailsService userDetailsService();
}
