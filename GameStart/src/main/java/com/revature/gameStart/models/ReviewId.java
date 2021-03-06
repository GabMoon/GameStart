package com.revature.gameStart.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a helper model to map the review id to the database
 */
public class ReviewId  implements Serializable {
    private User user;

    private Game game;

    public ReviewId() {

        super();
    }

    public ReviewId(User creator_id, Game game_id) {
        this.user = creator_id;
        this.game = game_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return user == reviewId.user && game == reviewId.game;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, game);
    }
}
