package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;


@Mapper
public interface AccountMapper {


    @Select("SELECT * FROM accounts WHERE username = #{username}")
    Optional<Account> findByUsername(String username);


    @Select("SELECT * FROM accounts")
    List<Account> findAll();


    @Insert("INSERT INTO accounts (username, password, role) " +
            "VALUES (#{username}, #{password}, #{role})"
    )
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
    void insert(Account account);
}
