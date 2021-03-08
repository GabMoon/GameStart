package com.revature.gameStart.controllers;


import com.revature.gameStart.models.Game;
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

    //Constructors --------------------------------------------------
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //Get -----------------------------------------------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getAllGames(){
        return gameService.getAllGames();
    }

//    @GetMapping
//    public List<Game> getGamesByDeveloper(@RequestParam String dev){
//        return gameService.getGameByDev(dev);
//    }

    @GetMapping(path = "/id/{id}")
    public Game getGameById(@PathVariable int id){
        return gameService.getGameById(id);
    }

    @GetMapping(path = "/name/{name}")
    public Game getGameByName(@PathVariable String name){
        return gameService.getGameByName(name);
    }

    @GetMapping(path = "/slug/{slug}")
    public Game getGameBySlug(@PathVariable String slug){
        return gameService.getGameBySlug(slug);
    }


}
