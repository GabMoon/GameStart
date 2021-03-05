package com.revature.gameStart.models;

import java.io.Serializable;
import java.util.Objects;

public class ReviewId  implements Serializable {
    private int creator;

    private int gameId;

    public ReviewId(int creator, int gameId) {
        this.creator = creator;
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return creator == reviewId.creator && gameId == reviewId.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(creator, gameId);
    }
}
