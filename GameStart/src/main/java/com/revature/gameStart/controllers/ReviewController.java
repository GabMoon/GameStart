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

import javax.servlet.http.HttpServletResponse;
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
    private final HttpServletResponse response;

    //Constructors --------------------------------------------------
    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, GameService gameService, HttpSession session, HttpServletResponse response){
        this.reviewService = reviewService;
        this.userService = userService;
        this.gameService = gameService;
        this.session = session;
        this.response = response;
    }

    //Get -----------------------------------------------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getAllReviews(){
        return reviewService.findAllReview();
    }
    //gets all user's reviews

    @GetMapping(path= "/user/")
    public List<Review> getAllUserReviews(){
        if(session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            return reviewService.getReviewsByUserId((Integer) session.getAttribute("userid"));
        }
        else {
            response.setStatus(403);
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
        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())){
            return reviewService.getReviewByUserAndGameId((Integer) session.getAttribute("userid"), gameId);
        }
        else{
           response.setStatus(403);
            return null;
        }

    }

//    @GetMapping(path = "/UserGameReview/{gameId}/{userId}")
//    public Review getReviewWithGameIdAndUserId(@PathVariable int gameId, @PathVariable int userId) {
////        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())){
//        return reviewService.getReviewByUserAndGameId(userId, gameId);
////        }
////        else{
////            response.setStatus(403);
////            return null;
////        }
//
//    }

    //Post -----------------------------------------------------------
//    @PostMapping(path = "/register/{gameId}/{userId}/{score}/{description}")
//    public void registerReview(@PathVariable int gameId,@PathVariable int userId, @PathVariable String description,@PathVariable int score){
////        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            reviewService.insertReview(userId, gameId, description, score);
////        }
////        else{
////            response.setStatus(403);
////        }
//    }

    @PostMapping(path = "/register/{gameId}/{score}/{description}")
    public void registerReview(@PathVariable int gameId,@PathVariable String description,@PathVariable int score){
        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
        reviewService.insertReview((Integer) session.getAttribute("userid"),gameId, description, score);
        }
        else{
            response.setStatus(403);
        }
    }

    //PATCH -----------------------------------------------------------
    @PatchMapping(path = "/update/{gameId}/{score}/{description}")
    public void updateDescriptionAndScore(@PathVariable int gameId,@PathVariable int score, @PathVariable String description){
        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            reviewService.updateReviewDescriptionAndScore((Integer) session.getAttribute("userid"), gameId, score, description);
        }
        else{
            response.setStatus(403);
        }

    }


    @PatchMapping(path = "/description/{gameId}/{description}")
    public void updateDescription( @PathVariable int gameId, @PathVariable String description){
        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            reviewService.updateReviewDescription((Integer) session.getAttribute("userid"), gameId, description);
        }
        else{
            response.setStatus(403);
        }
    }

    @PatchMapping(path = "/score/{gameId}/{score}")
    public void updateScore( @PathVariable int gameId, @PathVariable int score){
        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            reviewService.updateReviewScore((Integer) session.getAttribute("userid"), gameId, score);
        }
        else{
            response.setStatus(403);
        }
    }


    //Delete -----------------------------------------------------------
    @DeleteMapping(path = "/delete/{gameId}")
    public void deleteReview( @PathVariable int gameId){
        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            reviewService.deleteReviewByUserIdAndGameId((Integer) session.getAttribute("userid"), gameId);
        }
        else{
            response.setStatus(403);
        }
    }




}
