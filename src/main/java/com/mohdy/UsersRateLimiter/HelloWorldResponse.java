package com.mohdy.UsersRateLimiter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class HelloWorldResponse implements Serializable {
    String message;
}
