package net.mjduncan.watchlist.server.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/token")
public class AuthenticationController {


    @GetMapping
    public ResponseEntity<String> getCsrfToken() {
        return ResponseEntity.ok().build();
    }
}
