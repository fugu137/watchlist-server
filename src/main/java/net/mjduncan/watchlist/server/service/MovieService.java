package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.repository.MovieMapper;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;


    public List<Movie> getAllMovies() {
        return movieMapper.findAllMovies();
    }

    public void addUserMovie(Long userID, Movie movie) {
        try {
            movieMapper.insertMovie(movie);
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
        }

        movieMapper.insertUserMovie(userID, movie.getImdbID());
    }

    public Optional<Movie> getUserMovieByImdbID(Long userID, String imdbID) {
        return movieMapper.findUserMovieById(userID, imdbID);
    }

    public List<Movie> getUserMovies(Long userID) {
        return movieMapper.findAllUserMovies(userID);
    }

    public void removeUserMovie(Long userID, String imdbID) {
        movieMapper.removeUserMovie(userID, imdbID);
    }
}
