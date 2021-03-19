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

    @JsonIgnore
    private String token;

    //Constructors --------------------------------------------------
    public Principal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
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
}
