package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
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

    public void addMovieById(Long userId, AddMovieRequest addMovieRequest) {
        Movie movie = addMovieRequest.toMovie();
        movieMapper.insertMovie(movie);

        Long movieId = movie.getId();
        movieMapper.insertMovieByUserId(userId, movieId);
    }

    public List<Movie> getMoviesById(Long userId) {
        return movieMapper.findAllById(userId);
    }
}
