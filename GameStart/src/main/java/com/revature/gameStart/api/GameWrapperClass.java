package com.revature.gameStart.api;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is used to wrap a game object from RAWG api
 */
public class GameWrapperClass {

    private RawgGame[] results;
    private String next;

    public GameWrapperClass() {

    }

    GameWrapperClass(RawgGame[] games) {
        results = games;
    }

    public RawgGame[] getResults() {
        return results;
    }

    public GameWrapperClass setResults(RawgGame[] results) {
        this.results = results;
        return this;
    }

    public String getNext() {
        return next;
    }

    public GameWrapperClass setNext(String next) {
        this.next = next;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameWrapperClass that = (GameWrapperClass) o;
        return Arrays.equals(results, that.results) && Objects.equals(next, that.next);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(next);
        result = 31 * result + Arrays.hashCode(results);
        return result;
    }

    @Override
    public String toString() {
        return "GameWrapperClass{" +
                "results=" + Arrays.toString(results) +
                ", next='" + next + '\'' +
                '}';
    }
}
