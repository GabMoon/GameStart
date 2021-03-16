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

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepo;
    private GameService gameService;

    public ReviewService(){
        super();
    }

    @Autowired
    public ReviewService(ReviewRepository repo, GameService gameService) {
        super();
        this.gameService = gameService;
        this.reviewRepo = repo;
    }


    public void insertReview(int userId,int gameId, String description,int score){

        if(userId <= 0 || gameId <=0 || (score <0 || score >6)) throw  new InvalidRequestException();

        Optional<Review> review = reviewRepo.findReviewByUserAndGame(userId, gameId);
        if(review.isPresent()){
            throw new ResourceNotFoundException();

        }
        reviewRepo.insertReview(userId,gameId,description,score);

        gameService.updateGameRating(gameId);

    }

    public Review getReviewByUserAndGameId(int userId, int gameId) {

        if(gameId <= 0 || userId <= 0) throw new InvalidRequestException();

        Optional<Review> review = reviewRepo.findReviewByUserAndGame(userId, gameId);
        return review.orElseThrow(ResourceNotFoundException::new);
    }

    public List<Review> getReviewsByGameId(int gameId) {
        if(gameId <= 0) throw  new InvalidRequestException();

       //Optional<Review> review = reviewRepo.findById(gameId);
        List<Review> reviewList = reviewRepo.findReviewByGameId(gameId);

        if (reviewList.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return reviewList;
    }

    public List<Review> getReviewsByUserId(int userId) {
        if(userId <= 0) throw  new InvalidRequestException();

        List<Review> reviewList = reviewRepo.findReviewByUserId(userId);
        if(reviewList.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return reviewList;
    }

    public List<Review> findAllReview() {

        List<Review> allReviews = reviewRepo.findAll();

        if(allReviews.isEmpty()) throw new ResourceNotFoundException();

        return allReviews;
    }


    public void updateReviewDescriptionAndScore(int userId, int gameId,int score,String description){
        if(userId <= 0 || gameId<=0) throw new InvalidRequestException();

        getReviewByUserAndGameId(userId,gameId);


        reviewRepo.updateDescriptionAndScore(userId,gameId,score,description);
        gameService.updateGameRating(gameId);
    }

    public void updateReviewDescription(int userId, int gameId,String description){
        if(userId <= 0 || gameId<=0) throw new InvalidRequestException();

        getReviewByUserAndGameId(userId,gameId);


        reviewRepo.updateDescription(userId,gameId,description);
    }

    public void updateReviewScore(int userId, int gameId,int score){
        if(userId <= 0 || gameId<=0) throw  new InvalidRequestException();

        getReviewByUserAndGameId(userId,gameId);

        reviewRepo.updateScore(userId,gameId,score);
        gameService.updateGameRating(gameId);
    }


    public void deleteReviewByUserIdAndGameId(int userId,int gameId){
        if(userId <= 0 || gameId<=0) throw  new InvalidRequestException();

       getReviewByUserAndGameId(userId,gameId);

        reviewRepo.deleteReviewByUserIdAndGameId(userId,gameId);
        gameService.updateGameRating(gameId);
    }
    public boolean isReviewValid(Review review) {

        if (review == null) return false;
        if (review.getGame() == null) return false;
        if (review.getScore() == -1 || review.getScore() < 0 || review.getScore() > 5) return false;
        if (review.getUser() == null) return false;

        return true;
    }


}
