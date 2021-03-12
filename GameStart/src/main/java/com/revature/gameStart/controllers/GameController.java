package com.revature.gameStart.controllers;


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

    //Constructors --------------------------------------------------
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //Get -----------------------------------------------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getAllGames(){
//        List<Developer> devs = new ArrayList<>();
//        List<Publisher> pubs = new ArrayList<>();
//        List<Platform> plats = new ArrayList<>();
//        List<Genre> genres = new ArrayList<>();
//
//        List<Game> games = new ArrayList<>();
//
//        devs.add(new Developer(1, "AppleDeveloper"));
//        pubs.add(new Publisher(1, "BananaPublisher"));
//        plats.add(new Platform(1, "CakePlatform"));
//        genres.add(new Genre(1, "DogmaGenre"));
//
//        games.add(new Game("Apple", genres, "Description", 6, devs, pubs, plats));
//        return games;
        return gameService.getAllGames();
    }

    @GetMapping(path = "/id/{id}")
    public Game getGameById(@PathVariable int id){
//        List<Developer> devs = new ArrayList<>();
//        List<Publisher> pubs = new ArrayList<>();
//        List<Platform> plats = new ArrayList<>();
//        List<Genre> genres = new ArrayList<>();
//
//        devs.add(new Developer(1, "AppleDeveloper"));
//        pubs.add(new Publisher(1, "BananaPublisher"));
//        plats.add(new Platform(1, "CakePlatform"));
//        genres.add(new Genre(1, "DogmaGenre"));
//
//        Game gameWithNameApple = new Game("Apple", genres, "Description", 6, devs, pubs, plats);
//        return gameWithNameApple;
        return gameService.getGameById(id);
    }

    @GetMapping(path = "/name/{name}")
    public Game getGameByName(@PathVariable String name){
//        List<Developer> devs = new ArrayList<>();
//        List<Publisher> pubs = new ArrayList<>();
//        List<Platform> plats = new ArrayList<>();
//        List<Genre> genres = new ArrayList<>();
//
//        devs.add(new Developer(1, "AppleDeveloper"));
//        pubs.add(new Publisher(1, "BananaPublisher"));
//        plats.add(new Platform(1, "CakePlatform"));
//        genres.add(new Genre(1, "DogmaGenre"));
//
//        Game gameWithNameApple = new Game("Apple", genres, "Description", 6, devs, pubs, plats);
//        return gameWithNameApple;

        return gameService.getGameByName(name);
    }


}