package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.MovieSearchResult;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movies")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
    })
    List<Movie> findAllMovies();


    @Select("SELECT movies.imdb_id, movies.title FROM accounts_movies INNER JOIN movies ON movie_id = movies.imdb_id WHERE account_id = #{userId}")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
    })
    List<Movie> findAllUserMovies(Long userId);


    @Select("SELECT movies.imdb_id, movies.title FROM accounts_movies INNER JOIN movies ON movie_id = movies.imdb_id WHERE account_id = #{userId} AND imdb_id = #{movieId}")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
    })
    Optional<Movie> findUserMovieById(Long userId, String movieId);


    @Insert("INSERT INTO movies (imdb_id, title) VALUES (#{imdbID}, #{title})")
    void insertMovie(Movie movie);


    @Insert("INSERT INTO accounts_movies (account_id, movie_id) VALUES (#{userId}, #{movieId})")
    void insertUserMovie(Long userId, String movieId);

}
