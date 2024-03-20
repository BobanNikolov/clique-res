package com.example.cliqueres.service.user.converter;

import com.example.cliqueres.domain.UserAccount;
import com.example.cliqueres.service.user.dto.UserAccountDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAccountToDtoConverter implements Converter<UserAccount, UserAccountDto> {

  @Override
  public UserAccountDto convert(UserAccount source) {
    if (source == null) {
      return null;
    }
    var user = new UserAccountDto();

    user.setId(source.getId());
    user.setUsername(source.getUsername());
    user.setEmail(source.getEmail());
    user.setFirstName(source.getFirstName());
    user.setLastName(source.getLastName());
    user.setDisplayName(source.getDisplayName());

    return user;
  }
}
