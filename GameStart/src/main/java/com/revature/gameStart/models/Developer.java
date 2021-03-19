package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This the model class for a developer with annotations that maps to the database
 */
@Entity
@Table(name = "developer")
public class Developer {

    //Attributes ----------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "game_developer",
            joinColumns = @JoinColumn (name = "developer_id"),
            inverseJoinColumns = @JoinColumn (name = "game_id")
    )
    private List<Game> gamesDeveloped;

    //Constructors --------------------------------------------------

    public Developer() {
    }

    public Developer(String name) {
        this.name = name;
    }

    public Developer(int id, String name) {
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
        Developer developer = (Developer) o;
        return id == developer.id && Objects.equals(name, developer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
