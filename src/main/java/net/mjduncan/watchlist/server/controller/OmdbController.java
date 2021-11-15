package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.MovieWithDetails;
import net.mjduncan.watchlist.server.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * This class allows access to the OMDB movie database api at https://www.omdbapi.com
 */
@RestController
@RequestMapping("/omdb")
public class OmdbController {

    @Autowired
    private OmdbService omdbService;

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

    @GetMapping("/{imdbId}")
    public ResponseEntity<MovieWithDetails> getMovieInfo(@PathVariable String imdbID) {
        //TODO: implement
        return null;
    }
}
