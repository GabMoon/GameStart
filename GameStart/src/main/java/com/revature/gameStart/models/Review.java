package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(ReviewId.class)
@Table(name = "review")
public class Review {

    //Attributes ----------------------------------------------------
    @Column
    private String description;

    @Column(nullable = false)
    private int score;

    @Id
    @ManyToOne
    @JoinColumn(name="creator_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="game_id", nullable = false)
    private Game game;


    //Constructors --------------------------------------------------

    public Review() {
    }

    public Review(int score, Game gameId, User creatorId) {
        this.score = score;
        this.game = gameId;
        this.user = creatorId;
    }

    public Review(String description, int score, Game gameId, User creatorId) {
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    //Other ---------------------------------------------------------


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return score == review.score && Objects.equals(description, review.description) && Objects.equals(game, review.game) && Objects.equals(user, review.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, score, game, user);
    }

    @Override
    public String toString() {
        return "Review{" +
                "description='" + description + '\'' +
                ", score=" + score +
                ", game=" + game +
                ", user=" + user +
                '}';
    }
}
