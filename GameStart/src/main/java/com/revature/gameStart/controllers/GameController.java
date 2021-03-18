package com.revature.gameStart.controllers;


import com.revature.gameStart.api.RawgApi;
import com.revature.gameStart.api.RawgGame;
import com.revature.gameStart.models.*;
import com.revature.gameStart.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import java.util.*;

@RestController
@RequestMapping("/games")
public class GameController {
    //Attributes ----------------------------------------------------
    private final GameService gameService;
    private final RawgApi rawgApi;

    //Constructors --------------------------------------------------
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getAllGames(){

        return gameService.getAllGames();
    }

    @GetMapping(path = "/id/{id}")
    public Game getGameById(@PathVariable int id){

        return gameService.getGameById(id);
    }

    @GetMapping(path = "/top10")
    public List<Game> getTop10(){
        return gameService.getTop10Games();
    }

    @GetMapping(path = "/slug/{slug}")
    public Game getGameByName(@PathVariable String slug){

       Game game = gameService.getGameBySlug(slug);
//        if (game == null){
//            gameService.insertNewGame(slug);
//        Game newGame = gameService.getGameBySlug(slug);
//        return newGame;
//        }

        return game;
    }

    @GetMapping(path = "/like/{slug}")
    public List<Game> getLikeGameBySlug(@PathVariable String slug){

        List<Game> allLikeGames = gameService.getLikeGames(slug);

        return allLikeGames;
    }

    @GetMapping(path = "/like/{name}")
    public List<Game> getLikeGameByName(@PathVariable String slug){

        List<Game> allLikeGames = gameService.getLikeGames(slug);

        return allLikeGames;
    }

    @PostMapping(path = "/newGame/{slug}")
    public void addNewGame(@PathVariable String slug){

            gameService.insertNewGame(slug);

    }

    @GetMapping(path = "/search/{name}")
    public List<Game> getSimilarGamesName(String name) {
      return gameService.getGameByLikeName(name);
    };

    @PatchMapping(path = "/updateRating/{id}")
    public void updateRating(@PathVariable int id) {
        gameService.updateGameRating(id);
    }
}