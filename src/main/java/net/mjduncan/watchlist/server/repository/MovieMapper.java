package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.SortColumn;
import net.mjduncan.watchlist.server.model.SortOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movies")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
            @Result(property = "year", column = "year"),
            @Result(property = "synopsis", column = "synopsis"),
            @Result(property = "imdbRating", column = "imdb_rating"),
            @Result(property = "tomatoesRating", column = "tomatoes_rating"),
            @Result(property = "metacriticRating", column = "metacritic_rating"),
            @Result(property = "posterURL", column = "poster_url")
    })
    List<Movie> findAllMovies();


    @Select("SELECT * FROM movies ORDER BY ${sortColumn} ${sortOrder}")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
            @Result(property = "year", column = "year"),
            @Result(property = "imdbRating", column = "imdb_rating"),
            @Result(property = "tomatoesRating", column = "tomatoes_rating"),
            @Result(property = "metacriticRating", column = "metacritic_rating"),
            @Result(property = "posterURL", column = "poster_url")
    })
    List<Movie> findAllMoviesSortedBy(SortColumn sortColumn, SortOrder sortOrder);


    @Select("SELECT movies.imdb_id, movies.title, movies.year, movies.synopsis, movies.imdb_rating, movies.tomatoes_rating, movies.metacritic_rating, movies.poster_url " +
            "FROM accounts_movies INNER JOIN movies ON movie_id = movies.imdb_id " +
            "WHERE account_id = #{userId}")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
            @Result(property = "year", column = "year"),
            @Result(property = "synopsis", column = "synopsis"),
            @Result(property = "imdbRating", column = "imdb_rating"),
            @Result(property = "tomatoesRating", column = "tomatoes_rating"),
            @Result(property = "metacriticRating", column = "metacritic_rating"),
            @Result(property = "posterURL", column = "poster_url")
    })
    List<Movie> findAllUserMovies(Long userId);


    @Select("SELECT movies.imdb_id, movies.title, movies.year, movies.synopsis, movies.imdb_rating, movies.tomatoes_rating, movies.metacritic_rating, movies.poster_url " +
            "FROM accounts_movies INNER JOIN movies ON movie_id = movies.imdb_id " +
            "WHERE account_id = #{userId} AND imdb_id = #{movieId}")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
            @Result(property = "year", column = "year"),
            @Result(property = "synopsis", column = "synopsis"),
            @Result(property = "imdbRating", column = "imdb_rating"),
            @Result(property = "tomatoesRating", column = "tomatoes_rating"),
            @Result(property = "metacriticRating", column = "metacritic_rating"),
            @Result(property = "posterURL", column = "poster_url")
    })
    Optional<Movie> findUserMovieById(Long userId, String movieId);


    @Insert("INSERT INTO movies (imdb_id, title, year, synopsis, imdb_rating, tomatoes_rating, metacritic_rating, poster_url) " +
            "VALUES (#{imdbID}, #{title}, #{year}, #{synopsis}, #{imdbRating}, #{tomatoesRating}, #{metacriticRating}, #{posterURL})")
    void insertMovie(Movie movie);


    @Insert("INSERT INTO accounts_movies (account_id, movie_id) VALUES (#{userId}, #{movieId})")
    void insertUserMovie(Long userId, String movieId);
}
