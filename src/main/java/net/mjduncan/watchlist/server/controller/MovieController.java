package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.AccountService;
import net.mjduncan.watchlist.server.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping
    public ResponseEntity<String> addMovieByUser(@RequestBody AddMovieRequest addMovieRequest, Authentication authentication) {
        String username = authentication.getName();
        Optional<Account> account = accountService.getAccountByUsername(username);

        if (account.isPresent()) {
            Long id = account.get().getId();
            movieService.addMovieById(id, addMovieRequest);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMoviesByUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<Account> account = accountService.getAccountByUsername(username);

        if (account.isPresent()) {
            Long id = account.get().getId();
            return ResponseEntity.ok(movieService.getMoviesById(id));
        }
        return ResponseEntity.badRequest().build();
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handle(Exception e) {
//        System.out.println("Returning HTTP 400 Bad Request" + e);
//        return ResponseEntity.status(400).build();
//    }
}
