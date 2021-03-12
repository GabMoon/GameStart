package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "game_genre",
            joinColumns = @JoinColumn (name = "genre_id"),
            inverseJoinColumns = @JoinColumn (name = "game_id")
    )
    private List<Game> gamesGenres;

    public Genre() {

    }

    public Genre(String name) {
            this.name = name;
    }

    public Genre(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Genre setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }

    public List<Game> getGamesGenres() {
        return gamesGenres;
    }

    public Genre setGamesGenres(List<Game> gamesGenres) {
        this.gamesGenres = gamesGenres;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id && Objects.equals(name, genre.name) && Objects.equals(gamesGenres, genre.gamesGenres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gamesGenres);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gamesGenres=" + gamesGenres +
                '}';
    }
}
