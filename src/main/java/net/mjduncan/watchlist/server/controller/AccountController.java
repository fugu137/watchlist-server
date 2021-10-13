package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.CreateAccountRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping
    @PreAuthorize("authentication.principal.username == 'Michael' && hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        accountService.addAccount(createAccountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
