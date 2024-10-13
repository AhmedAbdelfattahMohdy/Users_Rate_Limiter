package com.mohdy.UsersRateLimiter.Services;

import com.mohdy.UsersRateLimiter.Repository.UserRepository;
import com.mohdy.UsersRateLimiter.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

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


    public void logToFile(String message) {
        String filePath = "F:\\After Graduation\\Giza Systems\\Mid Project\\Ubnormal Requests History.txt";
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(message);  // Writing message to the file
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public Integer getRateLimitForUser (String username){
        return userRepository.getRateLimitForUser(username);
    }
}


