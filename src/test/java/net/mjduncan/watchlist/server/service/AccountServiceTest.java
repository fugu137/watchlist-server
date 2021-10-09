package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.CreateAccountRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.repository.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;


    @Test
    void shouldGetAccountByUsername() {
        String username = "Freddie";
        String password = "fredsPassword";
        Account account = new Account(username, password);

        when(accountMapper.findByUsername(username)).thenReturn(Optional.of(account));
        Optional<Account> result = accountService.getAccountByUsername(username);

        verify(accountMapper, times(1)).findByUsername(username);
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(account));
    }

    @Test
    void shouldGetAllAccounts() {
        List<Account> accounts = List.of(new Account("Test", "test"), new Account("Name", "pass"));

        when(accountMapper.findAll()).thenReturn(accounts);
        List<Account> results = accountService.getAllAccounts();

        verify(accountMapper, times(1)).findAll();
        assertThat(results, is(accounts));
    }

    @Test
    void shouldInsertAccountWithEncodedPassword() {
        String username = "Jane";
        String password = "janesPassword";
        CreateAccountRequest accountRequest = new CreateAccountRequest(username, password);

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        Account account = accountRequest.toAccount(passwordEncoder);
        accountService.addAccount(accountRequest);

        verify(accountMapper, times(1)).insert(accountArgumentCaptor.capture());
        Account capturedAccount = accountArgumentCaptor.getValue();

        assertThat(capturedAccount.getUsername(), is(account.getUsername()));
        assertThat(capturedAccount.getPassword(), is(account.getPassword()));
    }
}
