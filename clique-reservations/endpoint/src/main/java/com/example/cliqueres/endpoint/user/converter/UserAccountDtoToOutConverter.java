package com.example.cliqueres.endpoint.user.converter;

import com.example.cliqueres.endpoint.user.dto.UserAccountOut;
import com.example.cliqueres.service.user.dto.UserAccountDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAccountDtoToOutConverter implements Converter<UserAccountDto, UserAccountOut> {
  @Override
  public UserAccountOut convert(UserAccountDto source) {
    if (source == null) {
      return null;
    }
    return UserAccountOut.builder()
        .id(source.getId())
        .username(source.getUsername())
        .email(source.getEmail())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .displayName(source.getDisplayName())
        .build();
  }
}
