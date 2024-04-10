package com.example.cliqueres.repository.useraccount;

import com.example.cliqueres.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>,
    UserAccountSearchRepository {

}
