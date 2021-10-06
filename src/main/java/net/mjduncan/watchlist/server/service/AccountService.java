package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.repository.AccountMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    private final AccountMapper accountMapper;

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

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
