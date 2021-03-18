package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game")
public class Game {

    //Attributes ----------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(columnDefinition = "integer default -1")
    private double rating;

    @Column
    private String slug;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", slug='" + slug + '\'' +
                ", background_image='" + background_image + '\'' +
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", platforms=" + platforms +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && rating == game.rating && Objects.equals(name, game.name) && Objects.equals(genres, game.genres) && Objects.equals(description, game.description) && Objects.equals(slug, game.slug) && Objects.equals(background_image, game.background_image) && Objects.equals(developers, game.developers) && Objects.equals(publishers, game.publishers) && Objects.equals(platforms, game.platforms) && Objects.equals(reviews, game.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genres, description, rating, slug, background_image, developers, publishers, platforms, reviews);
    }

    @Column
    private String background_image;

    @ManyToMany(mappedBy = "gamesDeveloped")
    private List<Developer> developers;

    @ManyToMany(mappedBy = "gamesPublished")
    private List<Publisher> publishers;

    @ManyToMany(mappedBy = "gamesPlatforms")
    private List<Platform> platforms;

    @OneToMany(mappedBy = "game", targetEntity = Review.class)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "gamesGenres")
    private List<Genre> genres;

    @ManyToMany(mappedBy = "gameFavorites")
    private List<User> userFavorite;

    //Constructors --------------------------------------------------
    public Game() {
        super();
    }

    public Game(int id) {
        this.id = id;
    }

    public Game(int id, String name, List<Genre> genres, String description, int rating) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.description = description;
        this.rating = rating;
    }

    public Game(String name, List<Genre> genres, List<Developer> developers, List<Publisher> publishers, List<Platform> platforms) {
        this.name = name;
        this.genres = genres;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
    }


    public Game( String name, List<Genre> genres, String description, int rating, List<Developer> developers, List<Publisher> publishers, List<Platform> platforms) {
        this(name, genres, developers, publishers, platforms);
        this.description = description;
        this.rating = rating;
    }

    public Game(String name, String description, String slug, String background_image, List<Developer> developers, List<Publisher> publishers, List<Platform> platforms,  List<Genre> genres) {
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.background_image = background_image;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
        this.reviews = reviews;
        this.genres = genres;
    }

    public Game(String name, List<Genre> genres, String description, int rating, String slug, String background_image, List<Developer> developers, List<Publisher> publishers, List<Platform> platforms) {
        this.name = name;
        this.genres = genres;
        this.description = description;
        this.rating = rating;
        this.slug = slug;
        this.background_image = background_image;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
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

    public List<Genre> getGenre() {
        return genres;
    }

    public void setGenre(List<Genre> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public String getSlug() {
        return slug;
    }

    public Game setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getBackground_image() {
        return background_image;
    }

    public Game setBackground_image(String background_image) {
        this.background_image = background_image;
        return this;
    }


    //Other ---------------------------------------------------------


}
