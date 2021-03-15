package com.revature.gameStart.models;

import com.revature.gameStart.util.RoleConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_user")
public class User {
    //Attributes ----------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "role_name", nullable = false)
    @Convert(converter = RoleConverter.class)

    private UserRole role;

    @OneToMany(mappedBy = "user", targetEntity = Review.class)
    private List<Review> reviews;

    //Constructors --------------------------------------------------
    public User() {
        super();
    }

    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(int id, String firstName, String lastName, String username, String password, String email, UserRole role) {
        this(firstName, lastName, username, password, email);

        this.id = id;
        this.role = role;

    }

//    public User(int id, String firstName, String lastName, String username, String password, String email, UserRole role, List<Game> favorites) {
//        this(id, firstName, lastName, username, password, email, role);
//
//        this.favorites = favorites;
//    }

    //Getters & Setters ---------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    //Others --------------------------------------------------------

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && role == user.role && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username)
                && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, email, role);
    }
}
