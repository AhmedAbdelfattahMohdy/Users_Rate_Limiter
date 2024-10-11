package com.mohdy.UsersRateLimiter.Repository;

import com.mohdy.UsersRateLimiter.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor // Lombok annotation to generate constructor for final fields
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

//    public UserRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

//    public List<String> getAllUsers() {
//        return jdbcTemplate.query(
//                "SELECT * FROM RateLimiter.users",
//                (ResultSet rs, int rowNum) -> rs.getString("username")
//        );
//    }

    // Get User
    public User getUserByUsername(String username){
        return  jdbcTemplate.queryForObject(
                "SELECT username, e_mail, age FROM RateLimiter.users where username = ?",
                (ResultSet rs, int rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("e_mail"),
                        rs.getInt("age")
                ),
                username
        );
    }
    // Get All Users
    public List<User> getAllUsers() {
        return jdbcTemplate.query(
                "SELECT username, e_mail, age FROM RateLimiter.users order by id",
                (ResultSet rs, int rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("e_mail"),
                        rs.getInt("age")
                )
        );
    }


    // Add User
//    public void addUser(String name,String e_mail) {
//        jdbcTemplate.update(
//                "INSERT INTO RateLimiter.users (username, e_mail) VALUES (?, ?)"
//                , name , e_mail
//        );
//    }
}
