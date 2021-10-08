package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.CreateAccountRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;


    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    @PreAuthorize("authentication.principal.username == 'Michael' && hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        Account account = createAccountRequest.toAccount(passwordEncoder);
        accountService.addAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
