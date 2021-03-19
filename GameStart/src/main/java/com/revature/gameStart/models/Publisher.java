package com.revature.gameStart.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This the model class for a publisher with annotations that maps to the database
 */
@Entity
@Table(name = "publisher")
public class Publisher {

    //Attributes ----------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;


    @ManyToMany
    @JoinTable(
            name = "game_publisher",
            joinColumns = @JoinColumn (name = "publisher_id"),
            inverseJoinColumns = @JoinColumn (name = "game_id")
    )
    private List<Game> gamesPublished;



    //Constructors --------------------------------------------------

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher(int id, String name) {
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
        Publisher publisher = (Publisher) o;
        return id == publisher.id && Objects.equals(name, publisher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
