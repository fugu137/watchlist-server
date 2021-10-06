package net.mjduncan.watchlist.server.persistence;

import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.repository.AccountMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;


    @Test
    void shouldFindAllAccounts() {
        List<Account> accounts = accountMapper.findAll();

        Account primaryAccount = accounts.get(0);
        assertThat(primaryAccount.getUsername(), is("Michael"));
    }

    @Test
    void shouldFindByUsername() {
        Optional<Account> accountOptional = accountMapper.findByUsername("Michael");

        assertThat(accountOptional.isPresent(), is(true));
        assertThat(accountOptional.get().getUsername(), is("Michael"));
    }

    @Test
    void shouldInsertAccount() {
        Account account = new Account("Bob", "bobby");

        accountMapper.insert(account);
        Optional<Account> result = accountMapper.findByUsername("Bob");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(account));
    }
}
