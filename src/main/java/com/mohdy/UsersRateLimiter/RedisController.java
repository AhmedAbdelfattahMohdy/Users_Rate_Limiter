package com.mohdy.UsersRateLimiter;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedisController {
    // Not nesessary to add @Autowired because (Lombok) & final
    final  RedisService redisService;

    @GetMapping("/helloworld")
    public ResponseEntity<HelloWorldResponse> sayHelloWorld() {
        return ResponseEntity.ok().body(new HelloWorldResponse("Hello World 1"));
    }

    // simulate redis
    @SneakyThrows
    @GetMapping("/long-time")
    public ResponseEntity<HelloWorldResponse> waitTask() {
//        Thread.sleep(62000);
        return ResponseEntity.ok().body(new HelloWorldResponse("long time hello world"));
    }

    @SneakyThrows
    @GetMapping("/long-time-cache/{name}")
    public ResponseEntity<HelloWorldResponse> waitTaskCache(@PathVariable String name) {
        String redisResult = (String) redisService.getData(name);
        String responseMessage = "Welcome : %s , Your exam degree is : 92";
        redisService.incKey(name + "_trials");
        if (redisResult == null){
            Thread.sleep(6000);
            responseMessage = String.format(responseMessage,name);
            redisService.saveData(name,responseMessage+"%");
        } else {
            // cache hit
            responseMessage = redisResult;
        }
        return ResponseEntity.ok().body(new HelloWorldResponse(responseMessage));
    }

    @GetMapping("/long-time-cache-auto/{name}")
    @SneakyThrows
    @Cacheable(value = "responseMessage", key = "#name")
    public HelloWorldResponse waitTaskCacheAuto(@PathVariable String name) {
        String responseMessage = String.format("Welcome: %s, Your exam degree: 90%%",name);
        Thread.sleep(6000);
        System.out.println("Call wait task with parameters: " + name);
        return new HelloWorldResponse(responseMessage);
    }
}
