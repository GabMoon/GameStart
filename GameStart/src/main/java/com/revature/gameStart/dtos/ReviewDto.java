package com.revature.gameStart.dtos;

import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.User;

import java.util.List;
import java.util.Objects;

/**
 * a review dto used to help transfer a review
 */
public class ReviewDto {

    private String description;
    private int score;
    private int userId;
    private int gameId;

    public ReviewDto() {
    }

    public ReviewDto(String description, int score, int userId, int gameId) {
        this.description = description;
        this.score = score;
        this.userId = userId;
        this.gameId = gameId;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "description='" + description + '\'' +
                ", score=" + score +
                ", userId=" + userId +
                ", gameId=" + gameId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return score == reviewDto.score && userId == reviewDto.userId && gameId == reviewDto.gameId && Objects.equals(description, reviewDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, score, userId, gameId);
    }
}