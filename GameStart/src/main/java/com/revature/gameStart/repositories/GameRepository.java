
package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findGameByName(String name);

    @Query("FROM Game WHERE slug = :name")
    Game findSlugGame(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM game g WHERE g.rating != -1 ORDER BY rating DESC LIMIT 10")
    Optional<List<Game>> findTop10RatedGames();

    @Modifying
    @Query("UPDATE Game SET rating = :rating WHERE id = :gameId")
    void updateRating(int gameId, double rating);

    @Query("FROM Game WHERE slug = :slug")
    Game getSlug(String slug);

    @Modifying
    @Query("UPDATE Game SET description = :description WHERE slug = :slug")
    void setDescription(String description, String slug);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO genre (name) VALUES (:genre)")
    void insertGenre(String genre);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_genre (game_id, genre_id) VALUES (:gameId, :genreId)")
    void insertGameGenre(int gameId, int genreId);

    @Query("FROM Genre")
    List<Genre> findAllGenre();

    @Query("FROM Genre WHERE name = :genre")
    Genre findGenre(String genre);
}
