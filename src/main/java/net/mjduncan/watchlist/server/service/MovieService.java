package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.repository.MovieMapper;
import net.mjduncan.watchlist.server.model.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovieService {

    private final MovieMapper movieMapper;

    public MovieService(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }


    public List<Movie> getAllMovies() {
        return movieMapper.findAll();
    }
}
