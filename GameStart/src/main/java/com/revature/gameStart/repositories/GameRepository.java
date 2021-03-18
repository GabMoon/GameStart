
package com.revature.gameStart.repositories;

import com.revature.gameStart.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("FROM Game where slug LIKE %:name%")
    List<Game> getLikeGames(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM game g WHERE g.name ILIKE %:name%")
    List<Game> getSimiliarGameNames(@Param("name")String name);

    //All statements needed for inserting into genre and game_genre table
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO genre (name) VALUES (:genre)")
    void insertGenre(String genre);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_genre (game_id, genre_id) VALUES (:gameId, :genreId)")
    void insertGameGenre(int gameId, int genreId);

    @Query("FROM Genre WHERE name = :genre")
    Genre findGenre(String genre);

    //All statements needed for inserting into publisher and game_publisher table
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO publisher (name) VALUES (:publisher)")
    void insertPublisher(String publisher);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_publisher (game_id, publisher_id) VALUES (:gameId, :publisherId)")
    void insertGamePublisher(int gameId, int publisherId);

    @Query("FROM Publisher WHERE name = :publisher")
    Publisher findPublisher(String publisher);

    //All statements needed for inserting into developer and game_developer table
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO developer (name) VALUES (:developer)")
    void insertDeveloper(String developer);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_developer (game_id, developer_id) VALUES (:gameId, :developerId)")
    void insertGameDeveloper(int gameId, int developerId);

    @Query("FROM Developer WHERE name = :developer")
    Developer findDeveloper(String developer);

    //All statements needed for inserting into platform and game_platform table
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO platform (name) VALUES (:platform)")
    void insertPlatform(String platform);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_platform (game_id, platform_id) VALUES (:gameId, :platformId)")
    void insertGamePlatform(int gameId, int platformId);

    @Query("FROM Platform WHERE name = :platform")
    Platform findPlatform(String platform);

}
