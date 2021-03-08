package com.revature.gameStart.api;

import com.revature.gameStart.models.Game;

import java.util.Arrays;

public class GameWrapperClass {

    Game[] results;

    public GameWrapperClass() {

    }

    GameWrapperClass(Game[] games) {
        results = games;
    }

    public Game[] getResults() {
        return results;
    }

    public GameWrapperClass setResults(Game[] results) {
        this.results = results;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameWrapperClass that = (GameWrapperClass) o;
        return Arrays.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(results);
    }

    @Override
    public String toString() {
        return "GameWrapperClass{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}