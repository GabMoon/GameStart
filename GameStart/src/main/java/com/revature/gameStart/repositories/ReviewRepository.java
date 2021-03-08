package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findReviewByUserAndGame(int userId, int gameId);
}
