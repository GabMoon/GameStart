package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(ReviewId.class)
public class Review {

    //Attributes ----------------------------------------------------
    @Column
    private String description;

    @Column(nullable = false)
    private int score;

    @Id @ManyToOne
    @JoinColumn(name = "game")
    @Column(nullable = false, name = "game_id")
    private int gameId;

    @Id @ManyToOne
    @JoinColumn(name = "user")
    @Column(nullable = false, name = "creator_id")
    private int creatorId;


    //Constructors --------------------------------------------------

    public Review() {
    }

    public Review(int score, int gameId, int creatorId) {
        this.score = score;
        this.gameId = gameId;
        this.creatorId = creatorId;
    }

    public Review(String description, int score, int gameId, int creatorId) {
        this(score, gameId, creatorId);

        this.description = description;
    }

    //Getters and Setters -------------------------------------------

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    //Other ---------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return score == review.score && gameId == review.gameId && creatorId == review.creatorId && Objects.equals(description, review.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, score, gameId, creatorId);
    }

    @Override
    public String toString() {
        return "Review{" +
                "description='" + description + '\'' +
                ", score=" + score +
                ", gameId=" + gameId +
                ", creatorId=" + creatorId +
                '}';
    }
}
