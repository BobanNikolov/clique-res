package com.example.cliqueres.service.impl;

import com.example.cliqueres.domain.exception.UserDoesNotExistException;
import com.example.cliqueres.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private static final String PASSWORD_1 = "boban1!";
  private static final String PASSWORD_2 = "stefan1!";
  private static final String PASSWORD_3 = "goran1!";
  private static final String PASSWORD_4 = "iva1!";
  private static final String PASSWORD_5 = "viki1!";

  @Override
  public boolean checkUser(String username, String password) {
    if (username.equals("clique") && (password.equals(PASSWORD_1) || password.equals(PASSWORD_2)
        || password.equals(PASSWORD_3) || password.equals(PASSWORD_4) || password.equals(
        PASSWORD_5))) {
      return true;
    } else {
      throw new UserDoesNotExistException("Корисникот не постои!");
    }
  }
}
