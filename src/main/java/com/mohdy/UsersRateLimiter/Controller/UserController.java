package com.mohdy.UsersRateLimiter.Controller;

import com.mohdy.UsersRateLimiter.Services.UserService;
import com.mohdy.UsersRateLimiter.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

//    @PostMapping("/addUser")
//    public String addUser(@RequestParam String name,@RequestParam String e_mail) {
//        userService.addUser(name,e_mail);
//        return "User added successfully";
//    }
}
