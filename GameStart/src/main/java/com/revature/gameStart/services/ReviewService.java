package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepo;


    public ReviewService(){
        super();
    }

    @Autowired
    public ReviewService(ReviewRepository repo) {
        super();
        this.reviewRepo = repo;
    }

    public void registerReview(Review newReview) {

        if(!isReviewValid(newReview)) throw new InvalidRequestException();

        Optional<Review> persistedReview = reviewRepo.findReviewByUserAndGame(newReview.getUser().getId(),newReview.getGame().getId());
        if(persistedReview.isPresent()){
            throw new ResourcePersistenceException();
        }


        reviewRepo.save(newReview);

    }

    public Review getReviewByUserAndGameId(int userId, int gameId) {

        if(gameId <= 0 || userId <= 0) throw new InvalidRequestException();

        Optional<Review> review = reviewRepo.findReviewByUserAndGame(userId, gameId);
        return review.orElse(null);
    }

    public Review getReviewsByGameId(int gameId) {
        if(gameId <= 0) throw  new InvalidRequestException();

        Optional<Review> review = reviewRepo.findById(gameId);
        return review.orElse(null);
    }

    public Review getReviewsByUserId(int userId) {
        if(userId <= 0) throw  new InvalidRequestException();

        Optional<Review> review = reviewRepo.findById(userId);
        return review.orElse(null);
    }

    public List<Review> findAllReview() {

        List<Review> allReviews = reviewRepo.findAll();

        if(allReviews.isEmpty()) throw new ResourceNotFoundException();

        return allReviews;
    }

    public void updateReview(Review newReview){
        if (!isReviewValid(newReview)) {
            throw new InvalidRequestException();
        }
       // System.out.println("Game id "+newReview.getGame().getId()+"User id"+newReview.getUser().getId());
       Optional<Review> persistedReview = reviewRepo.findReviewByUserAndGame(newReview.getUser().getId(),newReview.getGame().getId());
       // Optional<Review> persistedReview = reviewRepo.findReviewByUserAndGame(1,1);
        if(!persistedReview.isPresent()){
            System.out.println("value" + persistedReview.toString());
            throw new ResourceNotFoundException();

        }
        reviewRepo.save(newReview);
        //reviewRepo.updateReview(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription(), newReview.getScore());

    }

    public void updateReviewDescription(int userId, int gameId,String description){
        if(userId <= 0 || gameId<=0) throw new InvalidRequestException();

        Optional<Review> persistedReview = reviewRepo.findReviewByUserAndGame(userId,gameId);
        if(!persistedReview.isPresent()){
            throw new ResourceNotFoundException();
        }

        reviewRepo.updateDescription(userId,gameId,description);
    }

    public void updateReviewScore(int userId, int gameId,int score){
        if(userId <= 0 || gameId<=0) throw  new InvalidRequestException();

        Optional<Review> persistedReview = reviewRepo.findReviewByUserAndGame(userId,gameId);
        if(!persistedReview.isPresent()){
            throw new ResourceNotFoundException();
        }
        reviewRepo.updateScore(userId,gameId,score);
    }


    public void deleteReviewByUserIdAndGameId(int gameId,int userId){
        if(userId <= 0 || gameId<=0) throw  new InvalidRequestException();

        Optional<Review> persistedReview = reviewRepo.findReviewByUserAndGame(userId,gameId);
        if(!persistedReview.isPresent()){
            throw new ResourceNotFoundException();
        }

        reviewRepo.deleteReviewByUserIdAndGameId(gameId,userId);
    }
    public boolean isReviewValid(Review review) {

        if (review == null) return false;
        if (review.getGame() == null) return false;
        if (review.getScore() == -1 || review.getScore() < 0 || review.getScore() > 5) return false;
        if (review.getUser() == null) return false;

        return true;
    }


}
