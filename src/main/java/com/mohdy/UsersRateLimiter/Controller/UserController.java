package com.mohdy.UsersRateLimiter.Controller;
import com.mohdy.UsersRateLimiter.Services.RedisService;

import com.mohdy.UsersRateLimiter.Services.UserService;
import com.mohdy.UsersRateLimiter.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RedisService redisService;

    @SneakyThrows
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        String RedisUserName = (String) redisService.getData(username);
        User userResult = userService.getUserByUsername(username);

        int rateLimitDB = userService.getRateLimitForUser(username);

        System.out.println(RedisUserName);
        if (RedisUserName == null){        // cache miss // First request for the user
              redisService.incKey(username + "_trials");
              // save user_trials in redis with expiration after 60
              redisService.setExpiration(username + "_trials", 60);
              // save user_rateLimitDB in redis with expiration after 60
              redisService.saveDataWithExpiration(username + "_rateLimitDB", rateLimitDB, 60); //
              // Save username Key with expiration after 60
              redisService.saveDataWithExpiration(username, userResult.getUsername()+" val", 60);
              return userResult;
        } else {   // cache hit
            // Increment the trial count each time user makes a request
            redisService.incKey(username + "_trials");
            int current = (Integer)  redisService.getKeyValue(username + "_trials");

            if (current > rateLimitDB) {
                String logMessage = "there is abnormal behavior from user: " + username + " at " + LocalDateTime.now();
                System.out.println(logMessage);
                userService.logToFile(logMessage); // Logging the abnormal behavior to a file
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests");

            } else {
                return userResult;
            }

        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}

