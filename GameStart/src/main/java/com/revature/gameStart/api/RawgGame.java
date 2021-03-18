package com.revature.gameStart.api;

import com.revature.gameStart.models.*;

import java.util.List;
import java.util.Objects;

public class RawgGame {

    private int id;

    private String name;

    private List<Genre> genres;

    private String description;

    private List<Developer> developers;

    private List<Publisher> publishers;

    private List<PlatformWrapperClass> platforms;

    private List<Review> reviews;

    private String slug;

    private String background_image;

    @Override
    public String toString() {
        return "RawgGame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", platforms=" + platforms +
                ", reviews=" + reviews +
                ", slug='" + slug + '\'' +
                ", background_image='" + background_image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawgGame game = (RawgGame) o;
        return id == game.id && Objects.equals(name, game.name) && Objects.equals(genres, game.genres) && Objects.equals(description, game.description) && Objects.equals(developers, game.developers) && Objects.equals(publishers, game.publishers) && Objects.equals(platforms, game.platforms) && Objects.equals(reviews, game.reviews) && Objects.equals(slug, game.slug) && Objects.equals(background_image, game.background_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genres, description,  developers, publishers, platforms, reviews, slug, background_image);
    }

    public String getSlug() {
        return slug;
    }

    public RawgGame setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getBackground_image() {
        return background_image;
    }

    public RawgGame setBackground_image(String background_image) {
        this.background_image = background_image;
        return this;
    }

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


    public RawgGame( String name, List<Genre> genres, String description, List<Developer> developers,
                     List<Publisher> publishers, List<PlatformWrapperClass> platforms) {
        this(name, genres, developers, publishers, platforms);
        this.description = description;
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


}
