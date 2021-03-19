package com.revature.gameStart.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.dtos.ReviewDto;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import com.revature.gameStart.services.GameService;
import com.revature.gameStart.services.ReviewService;
import com.revature.gameStart.services.UserService;
import com.revature.gameStart.util.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
/**
 * the controller use to manipulate review
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    //Attributes ----------------------------------------------------
    private final ReviewService reviewService;
    private final UserService userService;
    private final GameService gameService;

    //Constructors --------------------------------------------------
    /**
     * Constructor for review that sets the services/repository/httpsession for the controller
     * @param reviewService review service
     * @param userService user service
     * @param gameService game service
     * @param session http session
     * @param response response
     */
    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, GameService gameService){
        this.reviewService = reviewService;
        this.userService = userService;
        this.gameService = gameService;
    }

    //Get -----------------------------------------------------------
    /**
     * /review endpoint that returns all the reviews
     * @return returns a list of reviews
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getAllReviews(HttpServletRequest request, HttpServletResponse response){
        return reviewService.findAllReview();
    }

    /**
     * an endpoint that gets all the review from a user id if the user has a role
     * @return returns a list of review by user id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path= "/user")
    public List<Review> getAllUserReviews(HttpServletRequest request, HttpServletResponse response){
        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else {
            return reviewService.getReviewsByUserId((principal.getId()));
        }

//
//        if(session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            return reviewService.getReviewsByUserId((Integer) session.getAttribute("userid"));
//        }
//        else {
//            response.setStatus(403);
//            return null;
//        }
    }


    /**
     * an endpoint that returns a list of reviews by a game id
     * @param id id of game
     * @return returns a list of reviews from a game
     */
    //get all reviews for a game
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/game/{id}")
    public List<Review> getReviewsWithGameId(@PathVariable int id)
    {
        return reviewService.getReviewsByGameId(id);
    }

    /**
     * an endpoint that gets a review by the game id and user id
     * @param gameId game id
     * @return returns a review from a game by a user
     */
    //get a reviews base on user id and game id
    @GetMapping(path = "/UserGameReview/{gameId}")

    public Review getReviewWithGameIdAndUserId(@PathVariable int gameId, HttpServletRequest request, HttpServletResponse response) {
        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else {
            response.setStatus(200);
            return reviewService.getReviewByUserAndGameId(principal.getId(), gameId);
        }

//        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())){
//            return reviewService.getReviewByUserAndGameId((Integer) session.getAttribute("userid"), gameId);
//        }
//        else{
//            response.setStatus(403);
//            return null;
//        }

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

    /**
     * an endpoint that registers a review given the user id and a game id. Makes a review with a score and description for that
     * game.
     * @param gameId game id
     * @param description description of the game
     * @param score score of the game
     */
    @PostMapping(path = "/register/{gameId}/{score}/{description}")
    public void registerReview(@PathVariable int gameId,@PathVariable String description,@PathVariable int score, HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            response.setStatus(201);
            reviewService.insertReview(principal.getId(), gameId, description, score);
        }

//
//        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            reviewService.insertReview((Integer) session.getAttribute("userid"), gameId, description, score);
//        }
//        else{
//            response.setStatus(403);
//        }
    }

    //PATCH -----------------------------------------------------------

    /**
     * an endpoint that updates a review with the user id and game id. This method will update both the description and the
     * score of a review
     * @param gameId game id
     * @param score score of the game
     * @param description description of the game
     */
    @PatchMapping(path = "/update/{gameId}/{score}/{description}")
    public void updateDescriptionAndScore(@PathVariable int gameId,@PathVariable int score, @PathVariable String description, HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            response.setStatus(201);
            reviewService.updateReviewDescriptionAndScore(principal.getId(), gameId, score, description);
        }


//        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            reviewService.updateReviewDescriptionAndScore((Integer) session.getAttribute("userid"), gameId, score, description);
//        }
//        else{
//            response.setStatus(403);
//        }

    }

    /**
     * an endpoint that will partially update a review with only its description by the user and game id.
     * @param gameId game id
     * @param description new description of the game
     */
    @PatchMapping(path = "/description/{gameId}/{description}")
    public void updateDescription( @PathVariable int gameId, @PathVariable String description, HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            response.setStatus(201);
            reviewService.updateReviewDescription((principal.getId()), gameId, description);
        }


//        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            reviewService.updateReviewDescription((Integer) session.getAttribute("userid"), gameId, description);
//        }
//        else{
//            response.setStatus(403);
//        }
    }

    /**
     * an endpoint that updates only the score of a review given the user id and game id
     * @param gameId game id
     * @param score new score of the new game
     */
    @PatchMapping(path = "/score/{gameId}/{score}")
    public void updateScore( @PathVariable int gameId, @PathVariable int score, HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            response.setStatus(201);
            reviewService.updateReviewScore(principal.getId(), gameId, score);
        }

//        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            reviewService.updateReviewScore((Integer) session.getAttribute("userid"), gameId, score);
//        }
//        else{
//            response.setStatus(403);
//        }
    }


    //Delete -----------------------------------------------------------
    /**
     * an endpoint used to delete a specific review given a user id and a game id
     * @param gameId game id
     */
    @DeleteMapping(path = "/delete/{gameId}")
    public void deleteReview( @PathVariable int gameId, HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            reviewService.deleteReviewByUserIdAndGameId(principal.getId(), gameId);
        }

//
//        if (session.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            reviewService.deleteReviewByUserIdAndGameId((Integer) session.getAttribute("userid"), gameId);
//        }
//        else{
//            response.setStatus(403);
//        }
    }
}
