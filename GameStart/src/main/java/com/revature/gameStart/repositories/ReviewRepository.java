package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

//    @Modifying
//    @Query(value = "INSERT INTO review (description,score,game_id,creator_id) VALUES (:description,:score,:gameId,:userId)",nativeQuery = true)
//    void registerReview(int userId,int gameId,String description,int score);

    @Query("From Review WHERE user.id = :userId AND game.id = :gameId")
    Optional<Review> findReviewByUserAndGame(int userId, int gameId);

//    @Query("UPDATE Review SET description = :newDescription AND newScore WHERE user = userId AND game = gameId")
//    Review updateReview(@Param("userId")int userId, @Param("gameId") int gameId, @Param("newDescription") String newDescription, @Param("newScore") int newScore);

    @Query("FROM Review WHERE game.id = :gameId")
    List<Review> findReviewByGameId(int gameId);

    @Query("FROM Review WHERE user.id = :userId")
    List<Review> findReviewByUserId(int userId);

    @Query("UPDATE Review SET description = :newDescription WHERE user = :userId and game = :gameId")
    Review updateDescription(int userId, int gameId,String newDescription);

    @Query("UPDATE Review SET score = :newScore WHERE user = :userId and game = :gameId")
    Review updateScore(int userId, int gameId,int newScore);

    @Query("DELETE FROM Review WHERE user.id = :userId AND game.id = :gameId")
    Review deleteReviewByUserIdAndGameId(int userId, int gameId);
}
