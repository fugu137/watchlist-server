package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.repository.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;


    public Optional<Account> getAccountByUsername(String username) {
        return accountMapper.findByUsername(username);
    }

    public List<Account> getAllAccounts() {
        return accountMapper.findAll();
    }

    public void addAccount(Account account) {
        accountMapper.insert(account);
    }
}
