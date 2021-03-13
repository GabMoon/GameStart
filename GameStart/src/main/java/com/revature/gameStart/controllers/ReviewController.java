package com.revature.gameStart.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gameStart.dtos.ReviewDto;
import com.revature.gameStart.models.*;
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

    //Constructors --------------------------------------------------
    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, GameService gameService){
        this.reviewService = reviewService;
        this.userService = userService;
        this.gameService = gameService;
    }

    //Get -----------------------------------------------------------

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

    @GetMapping(path = "/register/{userId}/{gameId}/{score}/{description}")
    public void registerReview(@PathVariable int userId,@PathVariable int gameId,@PathVariable String description,@PathVariable int score){
        User existUser = userService.getUserById(userId);
        Game existGame = gameService.getGameById(gameId);
        Review newReview = new Review(description,score,existGame,existUser);
        reviewService.registerReview(newReview);
    }

    @GetMapping(path = "/registerReview/{reviewDto}")
    public void registerReview(@RequestParam(value = "ReviewDto") String reviewDto) throws JsonProcessingException {
        final ReviewDto review = new ObjectMapper().readValue(reviewDto, ReviewDto.class);
        User existUser = userService.getUserById(review.getUserId());
        Game existGame = gameService.getGameById(review.getGameId());
        Review newReview = new Review(review.getDescription(),review.getScore(),existGame,existUser);
        reviewService.registerReview(newReview);
    }


}
