package com.revature.gameStart.api;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.revature.gameStart.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component //maybe?
public class rawgAPI {

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

    @Autowired
    public rawgAPI(RestTemplate rawgClient) {
        this.rawgClient = rawgClient;
    }

    public List<Game> getGames() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        return rawgClient.getForEntity(rawgUrl+"/games", List<Game.class>).getBody();
    }
}
