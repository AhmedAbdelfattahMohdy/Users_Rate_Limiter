package com.mohdy.UsersRateLimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UsersRateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersRateLimiterApplication.class, args);
		System.out.println("Hello");
	}
}
