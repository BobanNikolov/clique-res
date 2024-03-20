package com.example.cliqueres.service.user;

import com.example.cliqueres.service.user.dto.UserAccountDto;
import com.example.cliqueres.service.user.dto.UserAccountPersistCommand;

public interface UserAccountService {
  UserAccountDto save(UserAccountPersistCommand user);
}
