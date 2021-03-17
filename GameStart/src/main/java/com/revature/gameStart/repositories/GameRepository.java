
package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findGameByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM game g WHERE g.rating != -1 ORDER BY rating DESC LIMIT 10")
    Optional<List<Game>> findTop10RatedGames();

    @Modifying
    @Query("UPDATE Game SET rating = :rating WHERE id = :gameId")
    void updateRating(int gameId, double rating);

    @Query("FROM Game WHERE slug = :slug")
    Game getSlug(String slug);

}
