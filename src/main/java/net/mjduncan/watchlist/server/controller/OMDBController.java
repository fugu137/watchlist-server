package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.OMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/omdb")
public class OMDBController {

    @Autowired
    private OMDBService omdbService;

    @GetMapping
    public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam String movieTitle) {
        ResponseEntity<SearchResults> response = omdbService.searchMoviesByTitle(movieTitle);
        HttpStatus status = response.getStatusCode();

        if (status.is2xxSuccessful() && response.getBody() != null) {
            List<Movie> movies = response.getBody().getMovies();
            return ResponseEntity.ok(movies);
        }

        return ResponseEntity.status(status).body(null);
    }

}
