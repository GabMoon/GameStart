package com.revature.gameStart.services;

import com.revature.gameStart.api.PlatformWrapperClass;
import com.revature.gameStart.api.RawgApi;
import com.revature.gameStart.api.RawgGame;
import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.GameRepository;
import com.revature.gameStart.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameService {
    //Attributes ----------------------------------------------------
    private final GameRepository gameRepo;
    private final ReviewRepository reviewRepository;
    private final RawgApi rawgApi;
//    private final ReviewService reviewService;

    //Constructors --------------------------------------------------
    @Autowired
    public GameService(GameRepository repo, ReviewRepository reviewRepository,RawgApi rawgApi ){
        super();
        this.gameRepo = repo;
        this.reviewRepository = reviewRepository;
        this.rawgApi = rawgApi;
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

    public List<Game> getLikeGames(String name){
        if(name.isEmpty()){
            throw new InvalidRequestException();
        }
        return gameRepo.getLikeGames(name);
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

    //Will get the game by slug name if not insert game into our database and populate it
    public Game getGameBySlug(String slug){

        if (slug == null || slug.trim().equals("")){
            throw new InvalidRequestException();
        }



        Game game = gameRepo.findSlugGame(slug);


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

    public void insertNewGame(String slug){
        Game newGame = new Game();
        RawgGame newRawgGame = rawgApi.getGame(slug);
        newGame.setName(newRawgGame.getName());
        newGame.setSlug(newRawgGame.getSlug());
        newGame.setBackground_image(newRawgGame.getBackground_image());
        gameRepo.save(newGame);
        populateGame(newRawgGame);
    }

    public void populateGame(RawgGame game) {
        // setting the description for the game
        gameRepo.setDescription(game.getDescription(), game.getSlug());

        //find the game inside the database with the slug name from rawg API
        Game DBgame = gameRepo.findSlugGame(game.getSlug());

        //get all genres in rawg API game and inserting into our database with the correct genre ID and game ID
        for(Genre genre: game.getGenres()) {

            Genre currentGenre = gameRepo.findGenre(genre.getName());
            if(currentGenre != null) {
                gameRepo.insertGameGenre(DBgame.getId(), currentGenre.getId());
            }else {
                gameRepo.insertGenre(genre.getName());
                Genre realGenre = gameRepo.findGenre(genre.getName());
                gameRepo.insertGameGenre(DBgame.getId(), realGenre.getId());
            }

        }

        //insert into game_publisher and publisher
        for(Publisher publisher: game.getPublishers()) {

            Publisher currentPublisher = gameRepo.findPublisher(publisher.getName());
            if(currentPublisher != null) {
                gameRepo.insertGamePublisher(DBgame.getId(), currentPublisher.getId());
            }else {
                gameRepo.insertPublisher(publisher.getName());
                Publisher realPublisher = gameRepo.findPublisher(publisher.getName());
                gameRepo.insertGamePublisher(DBgame.getId(), realPublisher.getId());
            }

        }

        //insert into game_developer and developer
        for(Developer developer: game.getDevelopers()) {

            Developer currentDeveloper = gameRepo.findDeveloper(developer.getName());
            if(currentDeveloper != null) {
                gameRepo.insertGameDeveloper(DBgame.getId(), currentDeveloper.getId());
            }else {
                gameRepo.insertDeveloper(developer.getName());
                Developer realDeveloper = gameRepo.findDeveloper(developer.getName());
                gameRepo.insertGameDeveloper(DBgame.getId(), realDeveloper.getId());
            }

        }

        //insert into game_developer and developer
        for(PlatformWrapperClass platform: game.getPlatforms()) {

            Platform currentPlatform = gameRepo.findPlatform(platform.getPlatform().getName());
            if(currentPlatform != null) {
                gameRepo.insertGamePlatform(DBgame.getId(), currentPlatform.getId());
            }else {
                gameRepo.insertPlatform(platform.getPlatform().getName());
                Platform realPlatform = gameRepo.findPlatform(platform.getPlatform().getName());
                gameRepo.insertGamePlatform(DBgame.getId(), realPlatform.getId());
            }

        }



    }
}
