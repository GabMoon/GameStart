
package com.revature.gameStart.repositories;

import com.revature.gameStart.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Game repository that stores game methods that interact with the database
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    /**
     * find a game inside the database by their name
     * @param name the name of the game
     * @return returns an optional game
     */
    Optional<Game> findGameByName(String name);

    /**
     * finds a game inside the database by the slug name
     * @param name slug name
     * @return returns a game by the slug name
     */
    @Query("FROM Game WHERE slug = :name")
    Game findSlugGame(String name);

    /**
     * finds the top 10 games by their rating
     * @return returns a list of the top 10 rated games
     */
    @Query(nativeQuery = true, value = "SELECT * FROM game g WHERE g.rating != -1 ORDER BY rating DESC LIMIT 10")
    Optional<List<Game>> findTop10RatedGames();

    /**
     * update a game's rating with the game's id
     * @param gameId game id
     * @param rating rating
     */
    @Modifying
    @Query("UPDATE Game SET rating = :rating WHERE id = :gameId")
    void updateRating(int gameId, double rating);

    /**
     * finds a game inside the database by the slug name
     * @param slug slug name
     * @return returns a game by the slug name
     */
    @Query("FROM Game WHERE slug = :slug")
    Game getSlug(String slug);

    /**
     * updates a game's description inside the database by their slug name
     * @param description description of the game
     * @param slug slug name
     */
    @Modifying
    @Query("UPDATE Game SET description = :description WHERE slug = :slug")
    void setDescription(String description, String slug);

    /**
     * get a list of games with similar game names
     * @param name name of game
     * @return returns a list of similar game by their name
     */
    @Query("FROM Game where slug LIKE %:name%")
    List<Game> getLikeGames(String name);

    /**
     * gets a list of 5 games where their names are similar
     * @param name game name
     * @return returns 5 games whose names are similar
     */
    @Query(nativeQuery = true, value = "SELECT * FROM game g WHERE g.name ILIKE %:name% LIMIT 5")
    List<Game> getSimiliarGameNames(@Param("name")String name);

    //All statements needed for inserting into genre and game_genre table

    /**
     * inserts a genre into the genre datbase
     * @param genre the genre to be inserted
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO genre (name) VALUES (:genre)")
    void insertGenre(String genre);

    /**
     * inserts into the game_genre junction table
     * @param gameId game id
     * @param genreId genre id
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_genre (game_id, genre_id) VALUES (:gameId, :genreId)")
    void insertGameGenre(int gameId, int genreId);

    /**
     * finds a genre by the name from the database
     * @param genre genre name
     * @return returns a genre
     */
    @Query("FROM Genre WHERE name = :genre")
    Genre findGenre(String genre);

    //All statements needed for inserting into publisher and game_publisher table

    /**
     * inserts a publisher into the database
     * @param publisher publisher name to be inserted
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO publisher (name) VALUES (:publisher)")
    void insertPublisher(String publisher);

    /**
     * inserts the publisher and associate game ids into the game_publisher junction table
     * @param gameId game id
     * @param publisherId publisher id
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_publisher (game_id, publisher_id) VALUES (:gameId, :publisherId)")
    void insertGamePublisher(int gameId, int publisherId);

    /**
     * finds a publisher by their name
     * @param publisher publisher name
     * @return returns a publisher
     */
    @Query("FROM Publisher WHERE name = :publisher")
    Publisher findPublisher(String publisher);

    //All statements needed for inserting into developer and game_developer table

    /**
     * inserts into the database a developer with their name
     * @param developer developer name
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO developer (name) VALUES (:developer)")
    void insertDeveloper(String developer);

    /**
     * inserts into the game_developer table with a game id and developer id
     * @param gameId game id
     * @param developerId developer id
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_developer (game_id, developer_id) VALUES (:gameId, :developerId)")
    void insertGameDeveloper(int gameId, int developerId);

    /**
     * finds the developer by their name
     * @param developer developer name
     * @return returns a developer
     */
    @Query("FROM Developer WHERE name = :developer")
    Developer findDeveloper(String developer);

    //All statements needed for inserting into platform and game_platform table

    /**
     * inserts into the platform table a platform name
     * @param platform platform name
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO platform (name) VALUES (:platform)")
    void insertPlatform(String platform);

    /**
     * inserts into the game_platform junction table with the game id and associated platform id
     * @param gameId game id
     * @param platformId platform id
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO game_platform (game_id, platform_id) VALUES (:gameId, :platformId)")
    void insertGamePlatform(int gameId, int platformId);

    /**
     * finds a platform in the platform table base on the platform name
     * @param platform platform name
     * @return returns a platform
     */
    @Query("FROM Platform WHERE name = :platform")
    Platform findPlatform(String platform);

}
