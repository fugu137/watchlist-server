package net.mjduncan.watchlist.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

import static net.mjduncan.watchlist.server.model.UserRole.ROLE_USER;


@JsonIgnoreProperties("password")
public class Account {

    private Long id;
    private final String username;
    private String password;
    private UserRole role = ROLE_USER;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && username.equals(account.username) && password.equals(account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return String.format("Id: %s, Username: %s, Password: %s", this.id, this.username, this.password);
    }
}
