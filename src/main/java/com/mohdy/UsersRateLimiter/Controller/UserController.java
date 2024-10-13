package com.mohdy.UsersRateLimiter.Controller;

import com.mohdy.UsersRateLimiter.HelloWorldResponse;
import com.mohdy.UsersRateLimiter.RedisService;

import com.mohdy.UsersRateLimiter.Repository.UserRepository;
import com.mohdy.UsersRateLimiter.Services.UserService;
import com.mohdy.UsersRateLimiter.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RedisService redisService;
    private final UserRepository userRepository;


    @SneakyThrows
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        String RedisUserName = (String) redisService.getData(username);  //  *
        User userResult = userService.getUserByUsername(username);    //  -
        System.out.println(RedisUserName);
        if (RedisUserName == null){        // cache miss // First request for the user
              redisService.incKey(username + "_trials");
              redisService.setExpiration(username + "_trials", 60);
              // Save user with expiration in Redis (60 seconds)
              redisService.saveDataWithExpiration(username, userResult.getUsername()+"uu", 60);
              return userResult;
        } else {   // cache hit
            // Increment the trial count each time user makes a request
            redisService.incKey(username + "_trials");
            Integer current = (Integer)  redisService.getKeyValue(username + "_trials");
            if (current > 5) {
                System.out.println("there is abnormal behavior from user: " + username + " at " + LocalDateTime.now());
                return null;
            } else {
                return userResult;
            }

        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // for test db
    @GetMapping("/users/{username}")
    public User getUserUser(@PathVariable String username){
        return userService.getUserByUsername(username);
    }
}


















//    @PostMapping("/addUser")
//    public String addUser(@RequestParam String name,@RequestParam String e_mail) {
//        userService.addUser(name,e_mail);
//        return "User added successfully";
//    }

