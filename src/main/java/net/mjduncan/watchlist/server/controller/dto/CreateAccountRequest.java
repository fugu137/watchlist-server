package net.mjduncan.watchlist.server.controller.dto;


import net.mjduncan.watchlist.server.model.Account;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CreateAccountRequest {

    private final String username;
    private final String password;

    public CreateAccountRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return new Account(username, passwordEncoder.encode(password));
    }
}
