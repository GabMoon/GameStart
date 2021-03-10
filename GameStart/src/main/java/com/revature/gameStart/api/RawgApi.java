package com.revature.gameStart.api;

import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;
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
        boolean found = false;
        try {
            props.load(new FileReader("GameStart/src/main/resources/application.properties"));
        } catch (IOException e) {
            found = true;
        }
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            found = true;
        }

        if (!found) try {
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public RawgApi(RestTemplateBuilder restTemplateBuilder) {
        this.rawgClient = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.rawgClient.setMessageConverters(messageConverters);
    }

    public RawgApi() {

    }


    public RawgGame[] getGames() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.headForHeaders()
        GameWrapperClass response = restTemplate.getForObject(rawgUrl+"/games", GameWrapperClass.class);

        RawgGame[] games = response.getResults();

        return games;

    }

    public RawgGame getGame(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(rawgUrl+"/games/"+name, RawgGame.class);
    }

    /**
     * Get a list of games from a specific page, with a specified number of games per page. Give a page size of -1 to
     *  use the default page size, and -1 for pageNumber to set no specific page.
     * @param pageSize The number of games to request from RAWG
     * @param pageNumber the page number to look at
     * @return returns an array of RawgGames.
     */
    public RawgGame[] getPaginatedGames(int pageSize, int pageNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));

        if (pageSize != -1) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rawgUrl+"/games")
                .queryParam("page", pageNumber)
    }

    public void saveGames() {

    }

    public Game convertRawgGame(RawgGame game) {
        List<Platform> platforms = new ArrayList<>();

        for (PlatformWrapperClass plat : game.getPlatforms()) {
            platforms.add(convertWrapperPlatform(plat));
        }

        Game conGame = new Game(game.getName(), game.getGenres(), game.getDescription(), game.getRating(),
                game.getDevelopers(), game.getPublishers(), platforms);

        return conGame;
    }

    public Platform convertWrapperPlatform(PlatformWrapperClass platform) {
        Platform newPlat = new Platform(platform.getPlatform().getId(), platform.getPlatform().getName());
    }
}
