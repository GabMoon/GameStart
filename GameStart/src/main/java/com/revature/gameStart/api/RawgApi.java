package com.revature.gameStart.api;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.revature.gameStart.models.Developer;
import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.Platform;
import com.revature.gameStart.models.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/myGames")
public class RawgApi {

    @Autowired
    private RestTemplate rawgClient;
    private String rawgUrl = "https://api.rawg.io/api";
    private static Properties props = new Properties();

    static {
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Autowired
//    public RawgApi(RestTemplateBuilder restTemplateBuilder) {
//        this.rawgClient = restTemplateBuilder.build();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        this.rawgClient.setMessageConverters(messageConverters);
//    }

    public RawgApi() {

    }


    public Game[] getGames() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

////        return rawgClient.getForEntity(rawgUrl+"/games/portal-2", Game.class).getBody();
//
////        Game games = rawgClient.getForObject(rawgUrl+"/games/portal-2", Game.class);

        RestTemplate restTemplate = new RestTemplate();
        //estTemplate.headForHeaders()
        GameWrapperClass response = restTemplate.getForObject(rawgUrl+"/games", GameWrapperClass.class);

        Game[] games = response.getResults();

        return games;

    }

    public Game getGame(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(rawgUrl+"/games/"+name, Game.class);
    }

}
