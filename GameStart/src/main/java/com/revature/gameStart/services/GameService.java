package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.models.Game;
import com.revature.gameStart.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameService {
    //Attributes ----------------------------------------------------
    private final GameRepository gameRepo;

    //Constructors --------------------------------------------------
    @Autowired
    public GameService(GameRepository repo){
        super();
        this.gameRepo = repo;
    }

    //Other ---------------------------------------------------------
    public List<Game> getAllGames(){
        List<Game> games;

        games = (List<Game>) gameRepo.findAll();
        if(games.isEmpty()){
          throw new ResourceNotFoundException();
        }
        return games;
    }

    public Game getGameById(int id){
        Optional<Game> game = gameRepo.findById(id);
        return game.orElse(null);
    }

    public Game getGameByName(String name){
        Optional<Game> game = gameRepo.findGameByName(name);
        return game.orElse(null);
    }
}
