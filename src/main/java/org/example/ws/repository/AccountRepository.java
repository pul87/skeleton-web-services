package org.example.ws.repository;

import org.example.ws.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByUsername(String username);
}
