package com.example.cliqueres.service.user.impl;

import com.example.cliqueres.domain.UserAccount;
import com.example.cliqueres.repository.extsearch.QueryRequest;
import com.example.cliqueres.repository.useraccount.UserAccountRepository;
import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import com.example.cliqueres.service.user.UserAccountSearchService;
import com.example.cliqueres.service.user.dto.UserAccountDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserAccountSearchServiceImpl implements UserAccountSearchService {
  private final UserAccountRepository repository;
  private final ConversionService conversionService;

  @Override
  public Page<UserAccountDto> searchUserAccount(GqlSearchFilter searchRequest) {
    final QueryRequest request = conversionService.convert(searchRequest, QueryRequest.class);
    Page<UserAccount> searched = repository.query(request);

    Page<UserAccountDto> result = new PageImpl<>(
        searched.stream()
            .map(dto -> conversionService.convert(dto, UserAccountDto.class))
            .filter(Objects::nonNull)
            .toList(),
        searched.getPageable(), searched.getTotalElements());

    return result;
  }
}
