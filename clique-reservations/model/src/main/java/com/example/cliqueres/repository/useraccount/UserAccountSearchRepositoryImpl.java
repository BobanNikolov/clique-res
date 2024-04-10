package com.example.cliqueres.repository.useraccount;

import com.example.cliqueres.domain.UserAccount;
import com.example.cliqueres.repository.extsearch.AbstractExtendedSearchRepository;
import com.example.cliqueres.repository.extsearch.predicateconverters.PredicateConverter;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UserAccountSearchRepositoryImpl extends
    AbstractExtendedSearchRepository<UserAccount> implements
    UserAccountSearchRepository {
  protected UserAccountSearchRepositoryImpl(EntityManager entityManager,
      List<PredicateConverter> converterList) {
    super(entityManager, converterList);
  }

  @Override
  public Class<UserAccount> getEntityClass() {
    return UserAccount.class;
  }

}
