package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.Objects;

public class Game {

    //Attributes ----------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column
    private String description;

    @Column(columnDefinition = "integer default -1")
    private int rating;

    @Column(nullable = false, name = "publisher_id")
    @ManyToOne
    @JoinColumn(name = "id")
    private int publisherId;
    //Constructors --------------------------------------------------

    public Game() {
    }

    public Game(String name, String genre, String description, int rating, int publisherId) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.publisherId = publisherId;
    }

    public Game(int id, String name, String genre, String description, int rating, int publisherId) {
        this(name, genre, description, rating, publisherId);

        this.id = id;
    }

    //Getters and Setters -------------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    //Other ---------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && rating == game.rating && publisherId == game.publisherId && Objects.equals(name, game.name) && Objects.equals(genre, game.genre) && Objects.equals(description, game.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, description, rating, publisherId);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", publisherId=" + publisherId +
                '}';
    }
}
