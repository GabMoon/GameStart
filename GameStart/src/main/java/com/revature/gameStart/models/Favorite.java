package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(FavoriteId.class)
@Table(name = "favorite")
public class Favorite {

    @Id
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="game_id", nullable = false)
    private Game game;

    public Favorite() {
    }

    public Favorite(User user, Game game) {
        this.user = user;
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(user, favorite.user) && Objects.equals(game, favorite.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, game);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "user=" + user +
                ", game=" + game +
                '}';
    }
}
