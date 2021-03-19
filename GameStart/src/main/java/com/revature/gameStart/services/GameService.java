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

/**
 * Game service class that has all the services for a game with exception checking the validation of the data
 */
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
    /**
     * constructor for game service that sets the game repository, review repository, and RAWG api
     */
    public GameService(GameRepository repo, ReviewRepository reviewRepository,RawgApi rawgApi ){
        super();
        this.gameRepo = repo;
        this.reviewRepository = reviewRepository;
        this.rawgApi = rawgApi;
//        this.reviewService = reviewService;
    }

    //Get ---------------------------------------------------------

    /**
     * return a list of all the games and checks if they are empty
     * @return returns a list of games
     */
    public List<Game> getAllGames(){
        List<Game> games;

        games = gameRepo.findAll();
        if(games.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return games;
    }

    /**
     * returns a list of the top 10 games or else throw a resource exception
     * @return returns a list of the top 10 rated games
     */
    public List<Game> getTop10Games(){

        return gameRepo.findTop10RatedGames().orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * return a list of games with similar names or throw an invalid request exception if the name is empty
     * @param name name of game
     * @return returns a list of games with similar name
     */
    public List<Game> getLikeGames(String name){
        if(name.isEmpty()){
            throw new InvalidRequestException();
        }
        return gameRepo.getLikeGames(name);
    }

    /**
     * updates the game rating with the game id by taking the average of the review scoring for the game
     * @param gameId game id
     */
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

    /**
     * get a game by the game id
     * @param id game id
     * @return returns a game by their id
     */
    public Game getGameById(int id){

        if (id < 0) {
            throw new InvalidRequestException();
        }

        return gameRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * gets a game by their database name and checks if the name is valid (not null and trims)
     * @param name name of game
     * @return returns a game by their database name
     */
    public Game getGameByName(String name){

        if (name == null || name.trim().equals("")){
            throw new InvalidRequestException();
        }
        Game game = gameRepo.findGameByName(name).orElseThrow(ResourceNotFoundException::new);

//        updateGameRating(game.getId());

        return game;
    }

    /**
     * finds a list of games by similar names and checks if the name is valid and the list of games isn't empty
     * @param name name of game
     * @return returns a like of games with similar names
     */
    public List<Game> getGameByLikeName(String name){
        if (name == null || name.trim().equals("")){
            throw new InvalidRequestException();
        }

        List<Game> game = gameRepo.getSimiliarGameNames(name);
        if(game == null) {
            throw new ResourceNotFoundException();
        }

        return game;
    }

    /**
     * get a game by the RAWG api slug and checks if the slug is valid
     * @param slug slug name
     * @return returns a game by the slug
     */
    //Will get the game by slug name if not insert game into our database and populate it
    public Game getGameBySlug(String slug){

        if (slug == null || slug.trim().equals("")){
            throw new InvalidRequestException();
        }



        Game game = gameRepo.findSlugGame(slug);
        return gameRepo.getSlug(slug);
    }

    /**
     * inserts a list of games into the datbase and checks if the list if empty or not
     * @param gameList list of games from the RAWG api
     */
    public void insertGame(List<Game> gameList) {
        if (gameList == null || gameList.isEmpty()) {
            throw new InvalidRequestException();
        }

        System.out.println(gameList.toString());

        for(Game game: gameList ) {
            gameRepo.save(game);
        }

    }

    /**
     * inserts an individual game from the RAWG api. This game contains more details than the list of games
     * @param slug slug name
     */
    public void insertNewGame(String slug){
        Game newGame = new Game();
        RawgGame newRawgGame = rawgApi.getGame(slug);
        newGame.setName(newRawgGame.getName());
        newGame.setSlug(newRawgGame.getSlug());
        newGame.setBackground_image(newRawgGame.getBackground_image());
        gameRepo.save(newGame);
        populateGame(newRawgGame);
    }

    /**
     * this method will set the description of a game in the database from the RAWG api and also their developer,
     * publisher, genre, and platform table along with their junction table
     * @param game RAWG game
     */
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

        //insert into game_platform and platform
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
