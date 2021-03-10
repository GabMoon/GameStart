package com.revature.gameStart.services;

import com.revature.gameStart.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RawgService {

    private final GameRepository gameRepo;

    @Autowired
    public RawgService(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

}
