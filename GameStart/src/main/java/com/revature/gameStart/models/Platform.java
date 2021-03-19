package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This the model class for a platform with annotations that maps to the database
 */
@Entity
@Table(name = "platform")
public class Platform {

    //Attributes ----------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn (name = "platform_id"),
            inverseJoinColumns = @JoinColumn (name = "game_id")
    )
    private List<Game> gamesPlatforms;

    //Constructors --------------------------------------------------

    public Platform() {
    }

    public Platform(String name) {
        this.name = name;
    }

    public Platform(int id, String name) {
        this.id = id;
        this.name = name;
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


    //Other ---------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform platform = (Platform) o;
        return id == platform.id && Objects.equals(name, platform.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
