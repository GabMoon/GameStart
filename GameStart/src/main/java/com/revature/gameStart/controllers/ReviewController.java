package com.revature.gameStart.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gameStart.dtos.ReviewDto;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import com.revature.gameStart.services.GameService;
import com.revature.gameStart.services.ReviewService;
import com.revature.gameStart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    //Attributes ----------------------------------------------------
    private final ReviewService reviewService;
    private final UserService userService;
    private final GameService gameService;
    private final ReviewRepository reviewRepository;

    //Constructors --------------------------------------------------
    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, GameService gameService, ReviewRepository reviewRepository){
        this.reviewService = reviewService;
        this.userService = userService;
        this.gameService = gameService;
        this.reviewRepository = reviewRepository;
    }

    //Get -----------------------------------------------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getAllReviews(){
        return reviewService.findAllReview();
    }
    //gets all user's reviews
    @GetMapping(path= "/user/{id}")
    public List<Review> getAllUserReviews(@PathVariable int id){

        return reviewService.getReviewsByUserId(id);
    }

    //get all reviews for a game
    @GetMapping(path = "/game/{id}")
    public List<Review> getReviewsWithGameId(@PathVariable int id) {
        return reviewService.getReviewsByGameId(id);
    }

    //get a reviews base on user id and game id
    @GetMapping(path = "/UserGameReview/{userId}/{gameId}")
    public Review getReviewWithGameIdAndUserId(@PathVariable int gameId, @PathVariable int userId) {
        return reviewService.getReviewByUserAndGameId(userId, gameId);
    }


    //Post -----------------------------------------------------------
    @PostMapping(path = "/register/{userId}/{gameId}/{score}/{description}")
    public void registerReview(@PathVariable int userId,@PathVariable int gameId,@PathVariable String description,@PathVariable int score){

        reviewService.insertReview(userId,gameId,description,score);
    }

    //PATCH -----------------------------------------------------------
    @PatchMapping(path = "/update/{userId}/{gameId}/{score}/{description}")
    public void updateDescriptionAndScore(@PathVariable int userId, @PathVariable int gameId,@PathVariable int score, @PathVariable String description){
        reviewService.updateReviewDescriptionAndScore(userId,gameId,score,description);

    }


    @PatchMapping(path = "/description/{userId}/{gameId}/{description}")
    public void updateDescription(@PathVariable int userId, @PathVariable int gameId, @PathVariable String description){
        reviewService.updateReviewDescription(userId,gameId,description);
    }

    @PatchMapping(path = "/score/{userId}/{gameId}/{score}")
    public void updateScore(@PathVariable int userId, @PathVariable int gameId, @PathVariable int score){
        reviewService.updateReviewScore(userId,gameId,score);
    }


    //Delete -----------------------------------------------------------
    @DeleteMapping(path = "/delete/{userId}/{gameId}")
    public void deleteReview(@PathVariable int userId, @PathVariable int gameId){
        reviewService.deleteReviewByUserIdAndGameId(userId,gameId);
    }




}
