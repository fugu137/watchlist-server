package net.mjduncan.watchlist.server.security;

import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.controller.AccountController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountController.class)
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserAccountDetailsService userAccountDetailsService;

    @MockBean
    private AccountController accountController;


    @Test
    void shouldRedirectOnLoginWithValidDetails() throws Exception {
        String username = "username";
        String password = "password";

        UserDetails userDetails = new User(username, passwordEncoder.encode(password), singletonList(new SimpleGrantedAuthority("USER")));
        when(userAccountDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        mockMvc.perform(formLogin("/login").user(username).password(password))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/movies"));
    }

    @Test
    void shouldRedirectToErrorPageOnLoginWithInvalidDetails() throws Exception {
        String username = "username";
        String password = "password";
        String wrongPassword = "drowssap";

        UserDetails userDetails = new User(username, passwordEncoder.encode(password), emptyList());
        when(userAccountDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        mockMvc.perform(formLogin("/login").user(username).password(wrongPassword))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login?error"));

    }

    @Test
    void shouldRedirectToLoginPageOnLogout() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
    }
}
