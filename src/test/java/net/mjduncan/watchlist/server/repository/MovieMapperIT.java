package net.mjduncan.watchlist.server.repository;


import net.mjduncan.watchlist.server.model.Movie;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieMapperIT {

    @Autowired
    private MovieMapper movieMapper;


    @Test
    void shouldFindAllMovies() {
        List<Movie> movies = movieMapper.findAll();

        Movie movie = new Movie("Gladiator");
        movie.setId(1L);

        assertThat(movies, hasItem(movie));
    }
}
