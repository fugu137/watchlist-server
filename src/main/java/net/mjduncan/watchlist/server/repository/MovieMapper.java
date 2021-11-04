package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movies")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
    })
    List<Movie> findAll();


    @Select("SELECT movies.imdb_id, movies.title FROM accounts_movies INNER JOIN movies ON movie_id = movies.imdb_id WHERE account_id = #{userId}")
    @Results({
            @Result(property = "imdbID", column = "imdb_id", id = true),
            @Result(property = "title", column = "title"),
    })
    List<Movie> findAllById(Long userId);


    @Insert("INSERT INTO movies (imdb_id, title) VALUES (#{imdbID}, #{title})")
    void insertMovie(Movie movie);


    @Insert("INSERT INTO accounts_movies (account_id, movie_id) VALUES (#{userId}, #{movieId})")
    void insertMovieByUserId(Long userId, String movieId);

}
