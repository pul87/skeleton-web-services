package org.example.ws.service;

import org.example.ws.model.Account;

public interface AccountService {

    public Account findByUsername(String username);
}
