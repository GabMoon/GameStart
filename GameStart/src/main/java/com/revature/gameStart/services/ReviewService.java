package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    public ArrayList<Review> reviews = new ArrayList<>();
    private ReviewRepository reviewRepo;

    List<User> users = new ArrayList<>();
    List<Game> games = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    @Autowired
    public ReviewService(ReviewRepository repo) {
        super();
        genres.add(new Genre(1,"fps"));
        users.add(new User(1, "ree", "ew", "APww", "Passwww", "aefp@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        games.add(new Game(1,"GTA", genres, "GTA game", 2));
        reviews.add(new Review("work", 5, games.get(0), users.get(0)));
        this.reviewRepo = repo;
    }

    public void registerReview(Review newReview) {

        if(!isReviewValid(newReview)) throw new InvalidRequestException();

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

    public boolean isReviewValid(Review review) {

        if (review == null) return false;
        if (review.getGame() == null) return false;
        if (review.getScore() == -1 || review.getScore() < 0 || review.getScore() > 5) return false;
        if (review.getUser() == null) return false;

        return true;
    }


}
