package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Review repository that has methods to interact with the database
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * inserts a review into the review table with a user id, game id, description and score of the game
     * @param userId user id
     * @param gameId game id
     * @param description game description
     * @param score game score
     */
    @Modifying
    @Query(value = "insert into review (description,score,game_id,creator_id) values (:description,:score,:gameId,:userId)",nativeQuery = true)
    void insertReview(int userId,int gameId,String description,int score);

    /**
     * finds an optional review by the user id and game id
     * @param userId user id
     * @param gameId game id
     * @return returns an optional review
     */
    @Query("From Review WHERE user.id = :userId AND game.id = :gameId")
    Optional<Review> findReviewByUserAndGame(int userId, int gameId);

//    @Query("UPDATE Review SET description = :newDescription AND newScore WHERE user = userId AND game = gameId")
//    Review updateReview(@Param("userId")int userId, @Param("gameId") int gameId, @Param("newDescription") String newDescription, @Param("newScore") int newScore);

    /**
     * finds a list of reviews by the game id
     * @param gameId game id
     * @return returns a list of reviews for a specific game
     */
    @Query("FROM Review WHERE game.id = :gameId")
    List<Review> findReviewByGameId(int gameId);

    /**
     * finds a list of reviews by the user id
     * @param userId user id
     * @return returns a list of reviews for a specific user
     */
    @Query("FROM Review WHERE user.id = :userId")
    List<Review> findReviewByUserId(int userId);

    /**
     * update the description of a game by the user
     * @param userId user id
     * @param gameId game id
     * @param newDescription new description for the review
     */
    @Modifying
    @Query("UPDATE Review SET description = :newDescription WHERE user.id = :userId and game.id = :gameId")
    void updateDescription(int userId, int gameId,String newDescription);

    /**
     * updates the review score for game by a user
     * @param userId user id
     * @param gameId game id
     * @param newScore new score to update
     */
    @Modifying
    @Query("UPDATE Review SET score = :newScore WHERE user.id = :userId and game.id = :gameId")
    void updateScore(int userId, int gameId,int newScore);

    /**
     * updates the description and score in a review by the user id and game id
     * @param userId user id
     * @param gameId game id
     * @param newScore new score for review
     * @param description new description for the review
     */
    @Modifying
    @Query("UPDATE Review SET description = :description, score = :newScore WHERE user.id = :userId and game.id = :gameId")
    void updateDescriptionAndScore(int userId, int gameId,int newScore,String description);

    /**
     * deletes a review by the user id and game id
     * @param userId user id
     * @param gameId game id
     */
    @Modifying
    @Query("DELETE FROM Review WHERE user.id = :userId AND game.id = :gameId")
    void deleteReviewByUserIdAndGameId(int userId, int gameId);
}
