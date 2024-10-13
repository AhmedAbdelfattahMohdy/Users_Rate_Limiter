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


    // Get User
    public User getUserByUsername(String username){
        return  jdbcTemplate.queryForObject(
                "SELECT username, e_mail, age FROM RateLimiter.users where username = ?",
                (ResultSet rs, int rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("e_mail"),
                        rs.getInt("age")
//                        rs.getInt("RateLimit")               //**
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
//                        rs.getInt("RateLimit")         //**
                )
        );
    }

    // Fetch Ratelimit for the user from PostgreSQL
    public Integer getRateLimitForUser(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT RateLimit FROM RateLimiter.users WHERE username = ?",
                new Object[]{username},
                Integer.class
        );
    }

}








// Add User
//    public void addUser(String name,String e_mail) {
//        jdbcTemplate.update(
//                "INSERT INTO RateLimiter.users (username, e_mail) VALUES (?, ?)"
//                , name , e_mail
//        );
//    }