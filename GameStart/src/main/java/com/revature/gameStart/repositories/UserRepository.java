package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Favorite;
import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * repository for user to interact with the database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * finds a user in the database by their username
     * @param username username
     * @return returns an optional user
     */
   // @Query(value = "FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);

    /**
     * finds a set of user by their user role
     * @param role user role
     * @return returns a set of users
     */
    //@Query(value = "FROM User u where u.role = :role")
    Set<User> findUsersByRole(UserRole role);

    /**
     * finds a user by their username and password
     * @param username username
     * @param password password
     * @return returns an optional user
     */
    //@Query(value = "FROM User u where u.username = :username AND u.password = :password")
    Optional<User> findUserByUsernameAndPassword(String username, String password);

    /**
     * finds the favorite game id by a user
     * @param userId user id
     * @param gameId game id
     * @return returns the id of a favorite game
     */
    @Query(value = "Select game_id FROM favorite f where f.game_id = :gameId AND f.user_id = :userId", nativeQuery = true)
    Optional<Integer> findFavoriteByGameIdAndUserId( int userId, int gameId);

    /**
     * finds all the favorite game of a user by their id
     * @param userId user id
     * @return returns a list of favorite games of the user
     */
    @Query("FROM Favorite where user.id = :userId")
    List<Favorite> findFavoriteByUserId(int userId);

    /**
     * inserts a favorite game by the user id and game id
     * @param userId user id
     * @param gameId game id
     */
    @Modifying
    @Query(value = "Insert Into favorite (game_id, user_id) values (:gameId, :userId)", nativeQuery = true)
    void InsertFavorite(int userId, int gameId);

    /**
     * deletes a game id from the favorite table by the user id
     * @param userId user id
     * @param gameId game id
     */
   @Modifying
   @Query(value = "Delete FROM favorite where game_id = :gameId AND user_id = :userId", nativeQuery = true)
   void DeleteFavorite(int userId, int gameId);

}
