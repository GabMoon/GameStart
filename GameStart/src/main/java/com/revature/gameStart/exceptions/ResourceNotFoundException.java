package com.revature.gameStart.exceptions;

/**
 * An exception that is called when a resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resource(s) found");
    }
}
