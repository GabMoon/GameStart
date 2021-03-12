package com.revature.gameStart.api;

import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.Platform;
import com.revature.gameStart.services.GameService;
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
    private static String token;

    static {
        boolean found = false;
        try {
            props.load(new FileReader("GameStart/src/main/resources/application.properties"));
            token = props.getProperty("rawgToken");
            found = true;
        } catch (IOException e) {
        }

        try {
            props.load(new FileReader("src/main/resources/application.properties"));
            token = props.getProperty("rawgToken");
            found = true;
        } catch (IOException e) {
        }

        try {
            token = System.getProperty("rawgToken");
            found = true;
        } catch (Exception e) {
        }

        if (!found) try {
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
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


    public RawgGame[] getGames() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.headForHeaders()
        GameWrapperClass response = restTemplate.getForObject(rawgUrl+"/games", GameWrapperClass.class);

        return response.getResults();
    }

    public RawgGame getGame(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(rawgUrl+"/games/"+name, RawgGame.class, game);
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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rawgUrl + "/games");

        if (pageSize != -1) {
            builder = builder.queryParam("page_size", pageSize);
        }
        if (pageNumber != -1) {
            builder = builder.queryParam("page", pageNumber);
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        GameWrapperClass response = restTemplate.getForObject(builder.toUriString(), GameWrapperClass.class, entity);

        return response.getResults();
    }

    public ArrayList<Game> getGamesFromPageSizeAndNumPages(int pageSize, int numPages) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("token", props.getProperty("rawgToken"));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rawgUrl + "/games");

        if (pageSize != -1) {
            builder = builder.queryParam("page_size", pageSize);
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        int counter = 0;
        ArrayList<Game> games = new ArrayList<>();

        String url = builder.toUriString();

        while (counter < numPages) {
            GameWrapperClass response = restTemplate.getForObject(url, GameWrapperClass.class, entity);

            RawgGame[] rawgGames;

            try {
                rawgGames = response.getResults();
            } catch (NullPointerException n) {
                return null;
            }

            for (RawgGame game: rawgGames) {
                games.add(convertRawgGame(game));
            }

            url = response.getNext();

            if (url == null) break;

            counter++;
        }

        return games;
    }

    public void saveGames() {
        RawgGame[] rawGames = getPaginatedGames(-1, -1);

        ArrayList<Game> games = new ArrayList<>();

        for (RawgGame game : rawGames) {
            games.add(convertRawgGame(game));
        }

        GameService service;
    }

    public Game convertRawgGame(RawgGame game) {
        List<Platform> platforms = new ArrayList<>();

        for (PlatformWrapperClass plat : game.getPlatforms()) {
            platforms.add(convertWrapperPlatform(plat));
        }

        return new Game(game.getName(), game.getGenres(), game.getDescription(), game.getRating(),
                game.getDevelopers(), game.getPublishers(), platforms);
    }

    public Platform convertWrapperPlatform(PlatformWrapperClass platform) {
        return new Platform(platform.getPlatform().getId(), platform.getPlatform().getName());
    }
}
