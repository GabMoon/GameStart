package com.revature.gameStart.models;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This is the user roles model
 */
public enum UserRole {
    //Attributes ----------------------------------------------------
    BASIC("Basic"), ADMIN("Admin"), DEV("Dev");

    private String name;

    UserRole(String name){
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString(){
        return name;
    }
}
