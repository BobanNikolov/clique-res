package com.example.cliqueres.service.user;

import com.example.cliqueres.service.search.gql.GqlSearchFilter;
import com.example.cliqueres.service.user.dto.UserAccountDto;
import org.springframework.data.domain.Page;

public interface UserAccountSearchService {
  Page<UserAccountDto> searchUserAccount(GqlSearchFilter request);
}
