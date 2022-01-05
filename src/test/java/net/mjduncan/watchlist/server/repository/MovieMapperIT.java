package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieMapperIT {

    @Autowired
    private MovieMapper movieMapper;


    @Test
    void shouldFindAllMovies() {
        Movie movie = new Movie("tt0172495", "Gladiator");

        List<Movie> results = movieMapper.findAllMovies();
        assertThat(results, hasItem(movie));
    }

    @Test
    void shouldFindAllUserMovies() {
        Movie gladiator = new Movie("tt0172495", "Gladiator");
        Movie alien = new Movie("tt0078748", "Alien");
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom");

        List<Movie> movies = List.of(gladiator, alien, moonriseKingdom);

        List<Movie> results = movieMapper.findAllUserMovies(1L);
        assertThat(results, is(movies));
    }

    @Test
    void shouldFindUserMovieById() {
        Movie gladiator = new Movie("tt0172495", "Gladiator");

        Optional<Movie> result = movieMapper.findUserMovieById(1L, "tt0172495");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(gladiator));
    }

    @Test
    void shouldInsertMovie() {
        Movie movie = new Movie("tt0780504", "Drive");

        List<Movie> resultsBefore = movieMapper.findAllMovies();
        assertThat(resultsBefore, not(hasItem(movie)));

        movieMapper.insertMovie(movie);

        List<Movie> resultsAfter = movieMapper.findAllMovies();
        assertThat(resultsAfter, hasItem(movie));
    }

    @Test
    void shouldInsertUserMovieById() {
        Movie movie = new Movie("tt0780504", "Drive");
        Long userId = 1L;

        movieMapper.insertMovie(movie);
        String movieId = movie.getImdbID();
        assertNotNull(movieId);

        movieMapper.insertUserMovie(userId, movieId);

        List<Movie> results = movieMapper.findAllUserMovies(userId);
        assertThat(results, hasItem(movie));
    }
}
