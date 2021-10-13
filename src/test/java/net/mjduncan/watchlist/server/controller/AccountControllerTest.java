package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.controller.dto.CreateAccountRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserAccountDetailsService userAccountDetailsService;

    @Captor
    private ArgumentCaptor<CreateAccountRequest> accountDtoCaptor;


    @Test
    @WithMockUser(username = "Michael", password = "anything", roles = "ADMIN")
    void shouldGetAllAccountsIfUserIsAuthorized() throws Exception {
        List<Account> accounts = List.of(new Account("Jacqueline", "password"), new Account("Test", "test123"));
        String accountsAsJson = new ObjectMapper().writeValueAsString(accounts);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json(accountsAsJson));

        verify(accountService).getAllAccounts();
    }

    @Test
    @WithMockUser(username = "Michael", roles = "USER")
    void shouldNotGetAllAccountsIfUserLacksAdminStatus() throws Exception {
        List<Account> accounts = List.of(new Account("Michael", "password"), new Account("Test", "test123"));

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldPassOnCreateAccountRequestWithCorrectDetails() throws Exception {
        String username = "Jacqueline";
        String password = "password";

        Account account = new Account(username, password);
        String jsonAccount = new ObjectMapper().writeValueAsString(account);

        mockMvc.perform(post("/accounts")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAccount))
                .andExpect(status().isCreated());

        verify(accountService, atLeastOnce()).addAccount(accountDtoCaptor.capture());

        CreateAccountRequest capturedDto = accountDtoCaptor.getValue();
        assertThat(capturedDto.getUsername(), is(account.getUsername()));
        assertThat(capturedDto.getPassword(), is(password));
    }
}
