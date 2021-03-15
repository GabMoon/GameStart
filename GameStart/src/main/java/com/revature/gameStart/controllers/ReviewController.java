package com.revature.gameStart.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gameStart.dtos.ReviewDto;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import com.revature.gameStart.services.GameService;
import com.revature.gameStart.services.ReviewService;
import com.revature.gameStart.services.UserService;
import com.revature.gameStart.util.RestResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    //Attributes ----------------------------------------------------
    private final ReviewService reviewService;
    private final UserService userService;
    private final GameService gameService;
    private final HttpSession session;

    //Constructors --------------------------------------------------
    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, GameService gameService,HttpSession session){
        this.reviewService = reviewService;
        this.userService = userService;
        this.gameService = gameService;
        this.session = session;
    }

    //Get -----------------------------------------------------------
    //return all reviews Dont Need It For Now
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Review> getAllReviews(){
//        return reviewService.findAllReview();
//    }

    @GetMapping(path= "/myReviews")
    public List<Review> getAllUserReviews(){
        if(session.getAttribute("userrole") == UserRole.BASIC.toString()) {

            return reviewService.getReviewsByUserId((Integer) session.getAttribute("userid"));

        }

        else{

            return null;
        }

    }

    //get all reviews for a game
    @GetMapping(path = "/game/{id}")
    public List<Review> getReviewsWithGameId(@PathVariable int id) {
        return reviewService.getReviewsByGameId(id);
    }

    //get a reviews base on user id and game id
    @GetMapping(path = "/UserGameReview/{gameId}")
    public Review getReviewWithGameIdAndUserId(@PathVariable int gameId) {
        return reviewService.getReviewByUserAndGameId((Integer) session.getAttribute("userid"), gameId);
    }


    //Post -----------------------------------------------------------
    @PostMapping(path = "/register/{gameId}/{score}/{description}")
    public void registerReview(@PathVariable int gameId,@PathVariable String description,@PathVariable int score){

        reviewService.insertReview((Integer) session.getAttribute("userid"),gameId,description,score);
    }

    //PATCH -----------------------------------------------------------
    @PatchMapping(path = "/update/{gameId}/{score}/{description}")
    public void updateDescriptionAndScore( @PathVariable int gameId,@PathVariable int score, @PathVariable String description){
        reviewService.updateReviewDescriptionAndScore((Integer) session.getAttribute("userid"),gameId,score,description);

    }


    @PatchMapping(path = "/update/description/{gameId}/{description}")
    public void updateDescription(@PathVariable int gameId, @PathVariable String description){
        reviewService.updateReviewDescription((Integer) session.getAttribute("userid"),gameId,description);
    }

    @PatchMapping(path = "/update/score/{gameId}/{score}")
    public void updateScore(@PathVariable int gameId, @PathVariable int score){
        reviewService.updateReviewScore((Integer) session.getAttribute("userid"),gameId,score);
    }


    //Delete -----------------------------------------------------------
    @DeleteMapping(path = "/delete/{gameId}")
    public void deleteReview(@PathVariable int gameId){
        reviewService.deleteReviewByUserIdAndGameId((Integer) session.getAttribute("userid"),gameId);
    }




}
