package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.repository.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieMapper movieMapper;


    @Test
    void shouldFindAllMovies() {
        List<Movie> movies = List.of(new Movie("1", "Gladiator"), new Movie("2", "Drive"), new Movie("3", "Thin Red Line"));

        when(movieMapper.findAllMovies()).thenReturn(movies);
        List<Movie> results = movieService.getAllMovies();

        verify(movieMapper, times(1)).findAllMovies();
        assertThat(results, is(movies));
    }

    @Test
    void shouldAddUserMovieByImdbId() {
        String movieId = "tt0780504";
        AddMovieRequest addMovieRequest = new AddMovieRequest(movieId);
        Movie movie = new Movie(addMovieRequest.getImdbID());
        Long userId = 333L;

        movieService.addUserMovie(userId, movie);

        verify(movieMapper, times(1)).insertMovie(movie);
        verify(movieMapper, times(1)).insertUserMovie(userId, movieId);
    }

    @Test
    void shouldNotAddMovieToGeneralDatabaseIfAlreadyExists() {
        String movieId = "tt0172495";
        AddMovieRequest addMovieRequest = new AddMovieRequest(movieId);
        Movie movie = new Movie(addMovieRequest.getImdbID());
        Long userId = 1L;

        when(movieMapper.findMovieById(movieId)).thenReturn(Optional.of(movie));

        movieService.addUserMovie(userId, movie);

        verify(movieMapper, times(0)).insertMovie(movie);
        verify(movieMapper, times(1)).insertUserMovie(userId, movieId);
    }

    @Test
    void shouldGetUserMovieByImdbId() {
        String imdbId = "tt0780504";
        Long userId = 10L;
        Movie movie = new Movie(imdbId, "Test Movie", 1900);

        when(movieMapper.findUserMovieById(userId, imdbId)).thenReturn(Optional.of(movie));
        Optional<Movie> result = movieService.getUserMovieByImdbID(userId, imdbId);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(movie));
        verify(movieMapper).findUserMovieById(userId, imdbId);
    }

    @Test
    void shouldGetUserMoviesByUserId() {
        List<Movie> movies = List.of(new Movie("1", "Drive"), new Movie("2", "True Grit"));
        Long userId = 10L;

        when(movieMapper.findAllUserMovies(userId)).thenReturn(movies);
        List<Movie> results = movieService.getUserMovies(userId);

        assertThat(results, is(movies));
        verify(movieMapper).findAllUserMovies(userId);
    }

    @Test
    void shouldRemoveUserMovie() {
        Long userId = 333L;
        String movieId = "tt0780504";

        movieService.removeUserMovie(userId, movieId);

        verify(movieMapper, times(1)).removeUserMovie(userId, movieId);
    }

}
