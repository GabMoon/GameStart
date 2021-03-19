package com.revature.gameStart.controllers;


import com.revature.gameStart.api.RawgApi;
import com.revature.gameStart.api.RawgGame;
import com.revature.gameStart.models.*;
import com.revature.gameStart.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import java.util.*;

/**
 * Controller class for the games endpoint.
 */
@RestController
@RequestMapping("/games")
public class GameController {
    //Attributes ----------------------------------------------------
    private final GameService gameService;
    private final RawgApi rawgApi;

    //Constructors --------------------------------------------------
    /**
     * Constructor for the game controller
     * @param gameService sets the game service
     * @param rawgApi sets the RAWG api
     */
    @Autowired
    public GameController(GameService gameService, RawgApi rawgApi) {
        this.gameService = gameService;
        this.rawgApi = rawgApi;
    }

    //Get -----------------------------------------------------------
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getAllGames(){
//        return "Its alive!!";
//    }
    /**
     * default /games endpoint to get a list of all the games
     * @return a list of games
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getAllGames(){

        return gameService.getAllGames();
    }

    /**
     * a games endpoint to retrieve a game with an id from the uri /id
     * @param id the id of a game
     * @return returns a game with the passed in id
     */
    @GetMapping(path = "/id/{id}")
    public Game getGameById(@PathVariable int id){

        return gameService.getGameById(id);
    }

    /**
     * an endpoint that returns the top 10 games by their rating
     * @return returns a list of the top rated games
     */
    @GetMapping(path = "/top10")
    public List<Game> getTop10(){
        return gameService.getTop10Games();
    }

    /**
     * an endpoint that returns a game by their slug name from the RAWG api
     * @param slug a name given by the RAWG api
     * @return returns a game with the specified slug
     */
    @GetMapping(path = "/slug/{slug}")
    public Game getGameBySlug(@PathVariable String slug){

       Game game = gameService.getGameBySlug(slug);
//        if (game == null){
//            gameService.insertNewGame(slug);
//        Game newGame = gameService.getGameBySlug(slug);
//        return newGame;
//        }

        return game;
    }

    /**
     * an endpoint use to get a list of games with similar slugs given a slug
     * @param slug a name given by the RAWG api
     * @return returns a list of games with similar slug
     */
    @GetMapping(path = "/like/{slug}")
    public List<Game> getLikeGameBySlug(@PathVariable String slug){

        List<Game> allLikeGames = gameService.getLikeGames(slug);

        return allLikeGames;
    }

    /**
     * an endpoint that returns a list of game with similar names from the database
     * @param slug this is the name inside the database
     * @return returns a list of games
     */
    @GetMapping(path = "/like/{name}")
    public List<Game> getLikeGameByName(@PathVariable String slug){

        List<Game> allLikeGames = gameService.getLikeGames(slug);

        return allLikeGames;
    }

    /**
     * an endpoint to get a game by the name inside the database
     * @param name the name of the game
     * @return returns a game
     */
    @GetMapping(path = "/name/{name}")
    public Game getGameByName(@PathVariable String name){

        Game foundGame = gameService.getGameByName(name);

        return foundGame;
    }

    /**
     * this endpoint adds a new game by their slug
     * @param slug slug name given by RAWG api
     */
    @PostMapping(path = "/newGame/{slug}")
    public void addNewGame(@PathVariable String slug){

            gameService.insertNewGame(slug);
    }

    /**
     * an endpoint that lets you search for a list of games that have similar names
     * @param name the name of a game
     * @return a list of similar named games
     */
    @GetMapping(path = "/search/{name}")
    public List<Game> getSimilarGamesName( @PathVariable String name) {
      return gameService.getGameByLikeName(name);
    };

    /**
     * an endpoint that updates a game's rating by their id
     * @param id id of a game
     */
    @PatchMapping(path = "/updateRating/{id}")
    public void updateRating(@PathVariable int id) {
        gameService.updateGameRating(id);

    }
}