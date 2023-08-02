package com.example.cliqueres.service.impl;

import com.example.cliqueres.domain.User;
import com.example.cliqueres.domain.exception.UserDoesNotExistException;
import com.example.cliqueres.repository.UserRepository;
import com.example.cliqueres.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String s)  {
        return userRepository.findByUsername(s).orElseThrow(()->new UserDoesNotExistException("Корисникот не постои"));
    }

}
