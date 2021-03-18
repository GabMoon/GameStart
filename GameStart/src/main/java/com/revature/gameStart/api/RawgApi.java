package com.revature.gameStart.api;

import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.Platform;
import com.revature.gameStart.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class is used to talk to the RAWG api and populate our database with a # of games.
 */
@Component
@EnableConfigurationProperties
public class RawgApi {

    private RestTemplate rawgClient;
    private String rawgUrl = "https://api.rawg.io/api";
    private static Properties props = new Properties();
//    private static String token;
    private final GameService gameService;

//    static {
//        boolean found = false;
//        try {
//            props.load(new FileReader("GameStart/src/main/resources/application.properties"));
//            found = true;
//        } catch (IOException e) {
//        }
//
//        try {
//            props.load(new FileReader("src/main/resources/application.properties"));
//            found = true;
//        } catch (IOException e) {
//        }
//
//        if (!found) try {
//            throw new FileNotFoundException();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Constructor to set our services
     * @param gameService game service
     */
    @Autowired
    public RawgApi(@Lazy GameService gameService) {
        this.gameService = gameService;
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.rawgClient = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.rawgClient.setMessageConverters(messageConverters);
    }


    /**
     * This saves a list of games(pageSize, numPages) to our database and inserts
     * a description, genre, platform, developer, name
     */
    @PostConstruct
    private void init()
    {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("In Post Construct");
        saveGames(5, 3);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Insert Data into Game, Platform, Genre, Developer, Publisher");
        insertExtraData();
    }

    /**
     * method used to get all the games from our games database and do a inividiual call to the rawg api with each game slug
     * and populate our database
     */
    public void insertExtraData(){
        List<Game> allgames = gameService.getAllGames();
        RawgGame rawgGame;
        for(Game theGame: allgames) {
            rawgGame = getGame(theGame.getSlug());
            gameService.populateGame(rawgGame);
        }
    }

    public RawgGame[] getGames() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
//        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        //restTemplate.headForHeaders()
        GameWrapperClass response = rawgClient.getForObject(rawgUrl+"/games", GameWrapperClass.class);

        return response.getResults();
    }

    public RawgGame getGame(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
//        headers.set("token", props.getProperty("rawgToken"));
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Game> game = new HttpEntity<>(headers);

        return rawgClient.getForObject(rawgUrl+"/games/"+name, RawgGame.class, game);
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
//        headers.set("token", props.getProperty("rawgToken"));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rawgUrl + "/games");

        if (pageSize != -1) {
            builder = builder.queryParam("page_size", pageSize);
        }
        if (pageNumber != -1) {
            builder = builder.queryParam("page", pageNumber);
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        GameWrapperClass response = rawgClient.getForObject(builder.toUriString(), GameWrapperClass.class, entity);

        return response.getResults();
    }

    public ArrayList<Game> getGamesFromPageSizeAndNumPages(int pageSize, int numPages) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
//        headers.set("token", props.getProperty("rawgToken"));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rawgUrl + "/games");

        if (pageSize != -1) {
            builder = builder.queryParam("page_size", pageSize);
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        int counter = 0;
        ArrayList<Game> games = new ArrayList<>();

        String url = builder.toUriString();

        while (counter < numPages) {
            GameWrapperClass response = rawgClient.getForObject(url, GameWrapperClass.class, entity);

            RawgGame[] rawgGames;

            try {
                assert response != null;
                rawgGames = response.getResults();
            } catch (Exception n) {
                return null;
            }

            for (RawgGame game: rawgGames) {
                Game newGame = convertRawgGame(game);
                newGame.setRating(-1);
                games.add(convertRawgGame(game));
            }

            url = response.getNext();

            if (url == null) break;

            counter++;
        }

        return games;
    }

    public ArrayList<Game> getGamesByParameters(int numberOfPages, Map<String, String> parameters) {
        int counter = 0;
        ArrayList<Game> games = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rawgUrl + "/games");

        for (Map.Entry<String, String> param : parameters.entrySet()) {
            builder = builder.queryParam(param.getKey(), param.getValue());
        }

        String url = builder.toUriString();

        while(counter < numberOfPages) {
            GameWrapperClass response = rawgClient.getForObject(url, GameWrapperClass.class, entity);

            RawgGame[] rawgGames;

            try {
                assert response != null;
                rawgGames = response.getResults();
            } catch (Exception n) {
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

    public void saveGames(int pageSize, int numPages) {
        ArrayList<Game> games = getGamesFromPageSizeAndNumPages(pageSize, numPages);

        gameService.insertGame(games);
    }

    public Game convertRawgGame(RawgGame game) {
        List<Platform> platforms = new ArrayList<>();

        for (PlatformWrapperClass plat : game.getPlatforms()) {
            platforms.add(convertWrapperPlatform(plat));
        }

        return new Game(game.getName(), game.getDescription(),  game.getSlug(),
                game.getBackground_image(), game.getDevelopers(), game.getPublishers(), platforms,game.getGenres());
    }

    public Platform convertWrapperPlatform(PlatformWrapperClass platform) {
        return new Platform(platform.getPlatform().getId(), platform.getPlatform().getName());
    }
}
