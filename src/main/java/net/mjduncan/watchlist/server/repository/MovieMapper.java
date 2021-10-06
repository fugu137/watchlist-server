package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movies")
    List<Movie> findAll();

}
