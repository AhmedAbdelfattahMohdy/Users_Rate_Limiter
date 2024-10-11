package com.mohdy.UsersRateLimiter.Services;

import com.mohdy.UsersRateLimiter.Repository.UserRepository;
import com.mohdy.UsersRateLimiter.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

//    public void addUser(String name, String e_mail) {
//        userRepository.addUser(name,e_mail);
//    }
}








//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public List<String> getUsers() {
//        return userRepository.getAllUsers();
//    }
//
//    public void addUser(String name) {
//        userRepository.addUser(name);
//    }
//}
