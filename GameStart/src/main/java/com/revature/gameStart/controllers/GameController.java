package com.revature.gameStart.controllers;


import com.revature.gameStart.api.RawgApi;
import com.revature.gameStart.api.RawgGame;
import com.revature.gameStart.models.*;
import com.revature.gameStart.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//        Game game = gameService.getGameBySlug(slug);

//        if(game.getDescription().isEmpty()) {

            RawgGame rawgGame = rawgApi.getGame(slug);

            gameService.populateGame(rawgGame);

//        }

        return null;
    }

}