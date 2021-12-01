package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.AccountService;
import net.mjduncan.watchlist.server.service.MovieService;
import net.mjduncan.watchlist.server.service.OmdbService;
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

    @Autowired
    private OmdbService omdbService;


    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping
    public ResponseEntity<String> addUserMovie(@RequestBody AddMovieRequest addMovieRequest, Authentication authentication) {
        String username = authentication.getName();
        Optional<Account> account = accountService.getAccountByUsername(username);

        if (account.isPresent()) {
            Long userID = account.get().getId();
            String imdbID = addMovieRequest.getImdbID();

            Optional<Movie> movieToFind = movieService.getUserMovieByImdbId(userID, imdbID);

            if (movieToFind.isPresent()) {
                return ResponseEntity.status(409).build();
            }

            ResponseEntity<Movie> response = omdbService.searchMoviesByID(imdbID);
            Movie movieToAdd = response.getBody();

            if (response.getStatusCodeValue() != 200 || movieToAdd == null) {
                return ResponseEntity.notFound().build();
            }

            movieService.addUserMovie(userID, movieToAdd);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getUserMovies(Authentication authentication) {
        String username = authentication.getName();
        Optional<Account> account = accountService.getAccountByUsername(username);

        if (account.isPresent()) {
            Long id = account.get().getId();

            return ResponseEntity.ok(movieService.getUserMovies(id));
        }

        return ResponseEntity.badRequest().build();
    }
}
