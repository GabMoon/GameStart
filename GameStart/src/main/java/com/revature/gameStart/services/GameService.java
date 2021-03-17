package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.Review;
import com.revature.gameStart.repositories.GameRepository;
import com.revature.gameStart.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class GameService {
    //Attributes ----------------------------------------------------
    private final GameRepository gameRepo;
    private final ReviewRepository reviewRepository;
//    private final ReviewService reviewService;

    //Constructors --------------------------------------------------
    @Autowired
    public GameService(GameRepository repo, ReviewRepository reviewRepository ){
        super();
        this.gameRepo = repo;
        this.reviewRepository = reviewRepository;
//        this.reviewService = reviewService;
    }

    //Get ---------------------------------------------------------
    public List<Game> getAllGames(){
        List<Game> games;

        games = gameRepo.findAll();
        if(games.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return games;
    }

    public List<Game> getTop10Games(){

        return gameRepo.findTop10RatedGames().orElseThrow(ResourceNotFoundException::new);
    }

    public void updateGameRating(int gameId) {

        int rating = 0;
        double avgB4Precision;
        List<Review> reviews = reviewRepository.findReviewByGameId(gameId);

        for(Review review: reviews) {
            rating += review.getScore();
        }

        avgB4Precision = rating/(double)reviews.size();

        double avg = BigDecimal.valueOf(avgB4Precision)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        gameRepo.updateRating(gameId, avg);

    }

    public Game getGameById(int id){

        if (id < 0) {
            throw new InvalidRequestException();
        }

        return gameRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Game getGameByName(String name){

        if (name == null || name.trim().equals("")){
            throw new InvalidRequestException();
        }

        return gameRepo.findGameByName(name).orElseThrow(ResourceNotFoundException::new);
    }

    public Game getGameBySlug(String slug){

        if (slug == null || slug.trim().equals("")){
            throw new InvalidRequestException();
        }

        return gameRepo.getSlug(slug);
    }

    public void insertGame(List<Game> gameList) {
        if (gameList == null || gameList.isEmpty()) {
            throw new InvalidRequestException();
        }

        System.out.println(gameList.toString());

        for(Game game: gameList ) {
            gameRepo.save(game);
        }


    }
}
