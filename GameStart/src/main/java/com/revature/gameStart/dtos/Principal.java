package com.revature.gameStart.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.gameStart.models.User;

/**
 * dto used to set the principal of a user
 */
public class Principal {
    //Attribute -----------------------------------------------------
    private int id;
    private String username;
    private String role;
    private String firstname;
    private String lastname;
    private String email;

    @JsonIgnore
    private String token;

    //Constructors --------------------------------------------------
    public Principal() {
        super();
    }

    public Principal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.email = user.getEmail();
    }


    //Getters and Setters -------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
