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

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   // @Query(value = "FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);

    //@Query(value = "FROM User u where u.role = :role")
    Set<User> findUsersByRole(UserRole role);

    //@Query(value = "FROM User u where u.username = :username AND u.password = :password")
    Optional<User> findUserByUsernameAndPassword(String username, String password);

    @Query(value = "Select game_id FROM favorite f where f.game_id = :gameId AND f.user_id = :userId", nativeQuery = true)
    Optional<Integer> findFavoriteByGameIdAndUserId( int userId, int gameId);

    @Query("FROM Favorite where user.id = :userId")
    List<Favorite> findFavoriteByUserId(int userId);


    @Modifying
    @Query(value = "Insert Into favorite (game_id, user_id) values (:gameId, :userId)", nativeQuery = true)
    void InsertFavorite(int userId, int gameId);

   @Modifying
   @Query(value = "Delete FROM favorite where game_id = :gameId AND user_id = :userId", nativeQuery = true)
   void DeleteFavorite(int userId, int gameId);

}
