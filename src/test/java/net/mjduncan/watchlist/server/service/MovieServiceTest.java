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

        when(movieMapper.findAll()).thenReturn(movies);
        List<Movie> results = movieService.getAllMovies();

        verify(movieMapper, times(1)).findAll();
        assertThat(results, is(movies));
    }

    @Test
    void shouldAddMovieByUserId() {
        String movieId = "tt0780504";
        AddMovieRequest addMovieRequest = new AddMovieRequest(movieId, "Drive");
        Movie movie = addMovieRequest.toMovie();
        Long userId = 333L;

        movieService.addMovieById(userId, addMovieRequest);

        verify(movieMapper, times(1)).insertMovie(movie);
        verify(movieMapper, times(1)).insertMovieByUserId(userId, movieId);
    }

    @Test
    void shouldGetMoviesById() {
        List<Movie> movies = List.of(new Movie("1", "Drive"), new Movie("2", "True Grit"));
        Long userId = 10L;

        when(movieMapper.findAllById(userId)).thenReturn(movies);
        List<Movie> results = movieService.getMoviesById(userId);

        assertThat(results, is(movies));
        verify(movieMapper).findAllById(userId);
    }

}
