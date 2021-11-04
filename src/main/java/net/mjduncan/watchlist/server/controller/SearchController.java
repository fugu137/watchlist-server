package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam String movieTitle) {
        ResponseEntity<SearchResults> response = searchService.searchMoviesByTitle(movieTitle);
        HttpStatus status = response.getStatusCode();

        if (status.is2xxSuccessful() && response.getBody() != null) {
            List<Movie> movies = response.getBody().getMovies();
            return ResponseEntity.ok(movies);
        }

        return ResponseEntity.status(status).body(null);
    }
}
