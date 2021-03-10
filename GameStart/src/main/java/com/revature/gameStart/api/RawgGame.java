package com.revature.gameStart.api;

import com.revature.gameStart.models.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RawgGame {

    private int id;

    private String name;

    private List<Genre> genres;

    private String description;

    private int rating;

    private List<Developer> developers;

    private List<Publisher> publishers;

    private List<PlatformWrapperClass> platforms;

    private List<Review> reviews;

    //Constructors --------------------------------------------------
    public RawgGame() {
        super();
    }

    public RawgGame(String name, List<Genre> genres, List<Developer> developers, List<Publisher> publishers,
                    List<PlatformWrapperClass> platforms) {
        this.name = name;
        this.genres = genres;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
    }


    public RawgGame( String name, List<Genre> genres, String description, int rating, List<Developer> developers,
                     List<Publisher> publishers, List<PlatformWrapperClass> platforms) {
        this(name, genres, developers, publishers, platforms);
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public RawgGame setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RawgGame setName(String name) {
        this.name = name;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public RawgGame setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RawgGame setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public RawgGame setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public RawgGame setDevelopers(List<Developer> developers) {
        this.developers = developers;
        return this;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public RawgGame setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
        return this;
    }

    public List<PlatformWrapperClass> getPlatforms() {
        return platforms;
    }

    public RawgGame setPlatforms(List<PlatformWrapperClass> platforms) {
        this.platforms = platforms;
        return this;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public RawgGame setReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    @Override
    public String toString() {
        return "RawgGame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", platforms=" + platforms +
                ", reviews=" + reviews +
                '}';
    }
}
