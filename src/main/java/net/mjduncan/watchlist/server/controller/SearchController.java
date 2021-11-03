package net.mjduncan.watchlist.server.controller;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Movie>> importBySearchTerm(@RequestParam String searchTerm) {
        List<Movie> results = searchService.searchMovies(searchTerm);

        if (results != null) {
            return ResponseEntity.ok(results);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
