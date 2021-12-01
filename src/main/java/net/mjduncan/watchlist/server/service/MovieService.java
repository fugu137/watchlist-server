package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.repository.MovieMapper;
import net.mjduncan.watchlist.server.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;


    public List<Movie> getAllMovies() {
        return movieMapper.findAll();
    }

    public void addUserMovie(Long userID, Movie movie) {
        movieMapper.insertMovie(movie);
        movieMapper.insertMovieByUserId(userID, movie.getImdbID());
    }

    public Optional<Movie> getUserMovieByImdbId(Long userID, String imdbID) {
        return movieMapper.findById(userID, imdbID);
    }

    public List<Movie> getUserMovies(Long userID) {
        return movieMapper.findAllById(userID);
    }

}
