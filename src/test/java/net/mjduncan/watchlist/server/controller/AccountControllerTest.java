package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.databind.MapperFeature;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
    Authentication authentication;

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
        String jsonAccount = mapper.writeValueAsString(account);

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

    @Test
    @WithMockUser(username = "Michael", roles = "USER")
    void shouldGetPrincipalIfUserLoggedIn() throws Exception {
        MvcResult response = mockMvc.perform(get("/accounts/principal")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseAsString = response.getResponse().getContentAsString();
        assertThat(responseAsString, containsString("Michael"));
    }

    @Test
    void shouldReturn401IfNoUserLoggedIn() throws Exception {
        mockMvc.perform(get("/accounts/principal")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }

}
