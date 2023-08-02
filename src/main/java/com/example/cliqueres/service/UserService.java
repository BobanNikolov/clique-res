package com.example.cliqueres.service;

import com.example.cliqueres.domain.User;

public interface UserService {
    User loadUserByUsername(String username);
}
