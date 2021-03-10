package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.InvalidRequestException;
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

    //Get ---------------------------------------------------------
    public List<Game> getAllGames(){
        List<Game> games;

        games = (List<Game>) gameRepo.findAll();
        if(games.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return games;
    }

    public Game getGameById(int id){

        if (id < 0) {
            throw new InvalidRequestException();
        }

        Optional<Game> game = gameRepo.findById(id);

        if (!game.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return game.orElse(null);
    }

    public Game getGameByName(String name){

        if (name == null || name.trim().equals("")){
            throw new InvalidRequestException();
        }

        Optional<Game> game = gameRepo.findGameByName(name);

        if (!game.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return game.orElse(null);
    }
}
