package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.Rating;
import net.mjduncan.watchlist.server.model.SortColumn;
import net.mjduncan.watchlist.server.model.SortOrder;
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
        Movie movie = new Movie("tt0172495", "Gladiator", 2001);

        List<Movie> results = movieMapper.findAllMovies();
        assertThat(results, hasItem(movie));
    }

    @Test
    void shouldFindMovieById() {
        Movie movie = new Movie("tt0172495", "Gladiator", 2001);

        Optional<Movie> result = movieMapper.findMovieById(movie.getImdbID());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(movie));
    }

    @Test
    void shouldFindAllMoviesWithAdditionalProperties() {
        Movie movie = new Movie("tt0172495", "Gladiator", 2001);

        List<Movie> results = movieMapper.findAllMovies();
        assertThat(results, hasItem(movie));

        Optional<Movie> result = results.stream().filter(m -> m.equals(movie)).findFirst();
        assertThat(result.isPresent(), is(true));

        assertThat(result.get().getImdbRating(), is("8.8/10"));
        assertThat(result.get().getTomatoesRating(), is("82%"));
        assertThat(result.get().getMetacriticRating(), is("80%"));

        assertThat(result.get().getPosterURL(), is("www.gladiator_poster_url.com"));
        assertThat(result.get().getSynopsis(), is("A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery."));
    }

    @Test
    void shouldFindAllUserMovies() {
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);
        Movie alien = new Movie("tt0078748", "Alien", 1989);
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom", 2007);

        List<Movie> movies = List.of(gladiator, alien, moonriseKingdom);

        List<Movie> results = movieMapper.findAllUserMovies(1L);
        assertThat(results, is(movies));
    }

    @Test
    void shouldFindAllUserMoviesWithAdditionalProperties() {
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);
        Movie alien = new Movie("tt0078748", "Alien", 1989);
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom", 2007);

        List<Movie> movies = List.of(gladiator, alien, moonriseKingdom);

        List<Movie> results = movieMapper.findAllUserMovies(1L);
        assertThat(results, is(movies));

        Optional<Movie> result = results.stream().filter(m -> m.equals(gladiator)).findFirst();
        assertThat(result.isPresent(), is(true));

        assertThat(result.get().getImdbRating(), is("8.8/10"));
        assertThat(result.get().getTomatoesRating(), is("82%"));
        assertThat(result.get().getMetacriticRating(), is("80%"));

        assertThat(result.get().getPosterURL(), is("www.gladiator_poster_url.com"));
        assertThat(result.get().getSynopsis(), is("A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery."));
    }

    @Test
    void shouldFindUserMovieById() {
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);

        Optional<Movie> result = movieMapper.findUserMovieById(1L, "tt0172495");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(gladiator));
    }

    @Test
    void shouldFindUserMoviesByIdWithAdditionalProperties() {
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);

        Optional<Movie> result = movieMapper.findUserMovieById(1L, "tt0172495");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(gladiator));

        assertThat(result.get().getImdbRating(), is("8.8/10"));
        assertThat(result.get().getTomatoesRating(), is("82%"));
        assertThat(result.get().getMetacriticRating(), is("80%"));

        assertThat(result.get().getPosterURL(), is("www.gladiator_poster_url.com"));
        assertThat(result.get().getSynopsis(), is("A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery."));
    }

    @Test
    void shouldInsertMovie() {
        Movie movie = new Movie("tt0780504", "Drive", 2010);

        List<Movie> resultsBefore = movieMapper.findAllMovies();
        assertThat(resultsBefore, not(hasItem(movie)));

        movieMapper.insertMovie(movie);

        List<Movie> resultsAfter = movieMapper.findAllMovies();
        Optional<Movie> result = resultsAfter.stream().filter(m -> m.equals(movie)).findFirst();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(movie));
    }

    @Test
    void shouldInsertMovieWithAdditionalProperties() {
        String synopsis = "This is a test synopsis for Drive.";

        Movie movie = new Movie("tt0780504", "Drive", 2010);
        movie.setRatings(List.of(new Rating("Rotten Tomatoes", "82%")));
        movie.setImdbRating("8.5");
        movie.setMetacriticRating("70%");
        movie.setPosterURL("www.drive_poster_url.com");
        movie.setSynopsis(synopsis);

        List<Movie> resultsBefore = movieMapper.findAllMovies();
        assertThat(resultsBefore, not(hasItem(movie)));

        movieMapper.insertMovie(movie);

        List<Movie> resultsAfter = movieMapper.findAllMovies();
        Optional<Movie> result = resultsAfter.stream().filter(m -> m.equals(movie)).findFirst();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(movie));

        assertThat(result.get().getImdbRating(), is("8.5"));
        assertThat(result.get().getTomatoesRating(), is("82%"));
        assertThat(result.get().getMetacriticRating(), is("70%"));

        assertThat(result.get().getPosterURL(), is("www.drive_poster_url.com"));
        assertThat(result.get().getSynopsis(), is(synopsis));
    }

    @Test
    void shouldInsertUserMovieById() {
        Movie movie = new Movie("tt0780504", "Drive", 2010);
        Long userId = 1L;

        movieMapper.insertMovie(movie);
        String movieId = movie.getImdbID();
        assertNotNull(movieId);

        movieMapper.insertUserMovie(userId, movieId);

        List<Movie> results = movieMapper.findAllUserMovies(userId);
        Optional<Movie> result = results.stream().filter(m -> m.equals(movie)).findFirst();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(movie));
    }

    @Test
    void shouldInsertUserMovieByIdWithAdditionalProperties() {
        Long userId = 1L;
        String movieId = "tt0780504";
        String synopsis = "This is a test synopsis for Drive.";

        Movie movie = new Movie(movieId, "Drive", 2010);
        movie.setRatings(List.of(new Rating("Rotten Tomatoes", "88%")));
        movie.setImdbRating("8.2");
        movie.setMetacriticRating("70%");
        movie.setPosterURL("www.drive_poster_url.com");
        movie.setSynopsis(synopsis);

        movieMapper.insertMovie(movie);
        movieMapper.insertUserMovie(userId, movieId);

        List<Movie> results = movieMapper.findAllUserMovies(userId);
        Optional<Movie> result = results.stream().filter(m -> m.equals(movie)).findFirst();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(movie));

        assertThat(result.get().getImdbRating(), is("8.2"));
        assertThat(result.get().getTomatoesRating(), is("88%"));
        assertThat(result.get().getMetacriticRating(), is("70%"));

        assertThat(result.get().getPosterURL(), is("www.drive_poster_url.com"));
        assertThat(result.get().getSynopsis(), is(synopsis));
    }

    @Test
    void shouldFindAllMoviesSortedByYear() {
        Movie alien = new Movie("tt0078748", "Alien", 1989);
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom", 2007);

        List<Movie> expected = List.of(alien, gladiator, moonriseKingdom);

        List<Movie> results = movieMapper.findAllMoviesSortedBy(SortColumn.YEAR, SortOrder.ASC);
        assertThat(results, is(expected));
    }

    @Test
    void shouldFindAllMoviesSortedByImdbRating() {
        Movie alien = new Movie("tt0078748", "Alien", 1989);
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom", 2007);

        List<Movie> expected = List.of(alien, gladiator, moonriseKingdom);

        List<Movie> results = movieMapper.findAllMoviesSortedBy(SortColumn.IMDB_RATING, SortOrder.DESC);
        assertThat(results, is(expected));
    }

    @Test
    void shouldFindAllMoviesSortedWithAdditionalProperties() {
        Movie alien = new Movie("tt0078748", "Alien", 1989);
        Movie gladiator = new Movie("tt0172495", "Gladiator", 2001);
        Movie moonriseKingdom = new Movie("tt1748122", "Moonrise Kingdom", 2007);

        List<Movie> expected = List.of(alien, gladiator, moonriseKingdom);

        List<Movie> results = movieMapper.findAllMoviesSortedBy(SortColumn.YEAR, SortOrder.ASC);
        assertThat(results, is(expected));

        Optional<Movie> result = results.stream().filter(m -> m.getImdbID().equals(alien.getImdbID())).findFirst();
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(alien));

        assertThat(result.get().getImdbRating(), is("9/10"));
        assertThat(result.get().getTomatoesRating(), is("92%"));
        assertThat(result.get().getMetacriticRating(), is("88%"));

        assertThat(result.get().getPosterURL(), is("www.alien_poster_url.com"));
        assertThat(result.get().getSynopsis(), is("After a space merchant vessel receives an unknown transmission as a distress call, one of the crew is attacked by a mysterious life form and they soon realize that its life cycle has merely begun."));
    }

    @Test
    void shouldRemoveUserMovie() {
        Long userID = 1L;
        String imdbID = "tt0172495";

        Movie gladiator = new Movie(imdbID, "Gladiator", 2001);

        List<Movie> moviesBefore = movieMapper.findAllUserMovies(userID);
        assertThat(moviesBefore.size(), is(3));
        assertThat(moviesBefore, hasItem(gladiator));

        movieMapper.removeUserMovie(userID, imdbID);

        List<Movie> moviesAfter = movieMapper.findAllUserMovies(userID);
        assertThat(moviesAfter.size(), is(2));
        assertThat(moviesAfter, not(hasItem(gladiator)));
    }
}
