package com.revature.gameStart.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a helper model to map the favorite id to the database
 */
public class FavoriteId implements Serializable {

    private User user;

    private Game game;

    public FavoriteId() {
    }

    public FavoriteId(User user, Game game) {
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
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(user, that.user) && Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, game);
    }

    @Override
    public String toString() {
        return "FavoriteId{" +
                "user=" + user +
                ", game=" + game +
                '}';
    }
}
