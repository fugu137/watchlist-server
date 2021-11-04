package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieMapperIT {

    @Autowired
    private MovieMapper movieMapper;


    @Test
    void shouldFindAllMovies() {
        Movie movie = new Movie("tt0172495", "Gladiator");

        List<Movie> results = movieMapper.findAll();
        assertThat(results, hasItem(movie));
    }

    @Test
    void shouldFindAllMoviesById() {
        Movie gladiator = new Movie("tt0172495", "Gladiator");
        Movie alien = new Movie("tt0078748", "Alien");
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom");

        List<Movie> movies = List.of(gladiator, alien, moonriseKingdom);

        List<Movie> results = movieMapper.findAllById(1L);
        assertThat(results, is(movies));
    }

    @Test
    void shouldInsertMovie() {
        Movie movie = new Movie("tt0780504", "Drive");

        List<Movie> resultsBefore = movieMapper.findAll();
        assertThat(resultsBefore, not(hasItem(movie)));

        movieMapper.insertMovie(movie);

        List<Movie> resultsAfter = movieMapper.findAll();
        assertThat(resultsAfter, hasItem(movie));
    }

    @Test
    void shouldInsertMovieByUserId() {
        Movie movie = new Movie("tt0780504", "Drive");
        Long userId = 1L;

        movieMapper.insertMovie(movie);
        String movieId = movie.getId();
        assertNotNull(movieId);

        movieMapper.insertMovieByUserId(userId, movieId);

        List<Movie> results = movieMapper.findAllById(userId);
        assertThat(results, hasItem(movie));
    }
}
