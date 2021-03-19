package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Review service class that has methods for calling the review repo and checking the validation of the data
 */
@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepo;
    private GameService gameService;

    public ReviewService(){
        super();
    }

    /**
     * constructor for review service that sets up the review repository and the game service
     * @param repo review repository
     * @param gameService game service
     */
    @Autowired
    public ReviewService(ReviewRepository repo, GameService gameService) {
        super();
        this.gameService = gameService;
        this.reviewRepo = repo;
    }

    /**
     * inserts a review in the review database with a user id, game id, and the review's description and score for the game. checks
     * the user and game id to be above 0. Also makes sure the score for the game is 0 - 5.
     * @param userId user id
     * @param gameId game id
     * @param description description of the game
     * @param score score of the game
     */
    public void insertReview(int userId,int gameId, String description,int score){

        if(userId <= 0 || gameId <=0 || (score <0 || score >6)) throw  new InvalidRequestException();

        Optional<Review> review = reviewRepo.findReviewByUserAndGame(userId, gameId);
        if(review.isPresent()){
            throw new ResourceNotFoundException();

        }
        reviewRepo.insertReview(userId,gameId,description,score);

        gameService.updateGameRating(gameId);

    }

    /**
     * get a review by the user and game ids. checks to see if the user and game ids are above 0.
     * @param userId user id
     * @param gameId game is
     * @return returns a review in a game made by a user
     */
    public Review getReviewByUserAndGameId(int userId, int gameId) {

        if(gameId <= 0 || userId <= 0) throw new InvalidRequestException();

        Optional<Review> review = reviewRepo.findReviewByUserAndGame(userId, gameId);
        return review.orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * gets a list of reviews by the game id. checks if the game id is 0 or above. Also checks to see
     * if the list of review returned is not empty
     * @param gameId game id
     * @return returns a list of review for a game
     */
    public List<Review> getReviewsByGameId(int gameId) {
        if(gameId <= 0) throw  new InvalidRequestException();

       //Optional<Review> review = reviewRepo.findById(gameId);
        List<Review> reviewList = reviewRepo.findReviewByGameId(gameId);

        if (reviewList.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reviewList;
    }

    /**
     * gets a list of reviews by the user with their id. checks to see if the user id is 0 or above. also
     * checks if the list of reviews returns is not empty
     * @param userId user id
     * @return returns a list of reviews made by a user
     */
    public List<Review> getReviewsByUserId(int userId) {
        if(userId <= 0) throw  new InvalidRequestException();

        List<Review> reviewList = reviewRepo.findReviewByUserId(userId);
        if(reviewList.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return reviewList;
    }

    /**
     * finds all the reviews made. checks if the list is not empty.
     * @return returns a list of all reviews
     */
    public List<Review> findAllReview() {

        List<Review> allReviews = reviewRepo.findAll();

        if(allReviews.isEmpty()) throw new ResourceNotFoundException();

        return allReviews;
    }

    /**
     * updates a review by the game and user ids. sets the review's new score and description.
     * @param userId user id
     * @param gameId game id
     * @param score new score for the game
     * @param description new description for the review
     */
    public void updateReviewDescriptionAndScore(int userId, int gameId,int score,String description){
        if(userId <= 0 || gameId<=0) throw new InvalidRequestException();

        getReviewByUserAndGameId(userId,gameId);


        reviewRepo.updateDescriptionAndScore(userId,gameId,score,description);
        gameService.updateGameRating(gameId);
    }

    /**
     * update the review description by the user id and game id
     * @param userId user id
     * @param gameId game id
     * @param description new description of a review
     */
    public void updateReviewDescription(int userId, int gameId,String description){
        if(userId <= 0 || gameId<=0) throw new InvalidRequestException();

        getReviewByUserAndGameId(userId,gameId);


        reviewRepo.updateDescription(userId,gameId,description);
    }

    /**
     * update the review score by the user id and game id
     * @param userId user id
     * @param gameId game id
     * @param score new score for the review
     */
    public void updateReviewScore(int userId, int gameId,int score){
        if(userId <= 0 || gameId<=0) throw  new InvalidRequestException();

        getReviewByUserAndGameId(userId,gameId);

        reviewRepo.updateScore(userId,gameId,score);
        gameService.updateGameRating(gameId);
    }

    /**
     * delete a review by the user id and game id. checks if the user id and game id is valid.
     * @param userId user id
     * @param gameId game id
     */
    public void deleteReviewByUserIdAndGameId(int userId,int gameId){
        if(userId <= 0 || gameId<=0) throw  new InvalidRequestException();

       getReviewByUserAndGameId(userId,gameId);

        reviewRepo.deleteReviewByUserIdAndGameId(userId,gameId);
        gameService.updateGameRating(gameId);
    }

    /**
     * helper function to check if the review is a valid review
     * @param review review
     * @return returns true if the review is valid else false
     */
    public boolean isReviewValid(Review review) {

        if (review == null) return false;
        if (review.getGame() == null) return false;
        if (review.getScore() == -1 || review.getScore() < 0 || review.getScore() > 5) return false;
        if (review.getUser() == null) return false;

        return true;
    }


}
