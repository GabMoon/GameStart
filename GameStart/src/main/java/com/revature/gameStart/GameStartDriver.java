package com.revature.gameStart;

import com.revature.gameStart.api.RawgApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GameStartDriver {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {

        SpringApplication.run(GameStartDriver.class, args);


    }
}
