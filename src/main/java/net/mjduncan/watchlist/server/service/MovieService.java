package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.repository.MovieMapper;
import net.mjduncan.watchlist.server.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;


    public List<Movie> getAllMovies() {
        return movieMapper.findAll();
    }
}
