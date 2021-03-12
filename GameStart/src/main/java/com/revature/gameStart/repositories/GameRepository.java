
package com.revature.gameStart.repositories;

import com.revature.gameStart.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findGameByName(String name);
    Optional<Game> findGameBySlug(String slug);
}
