package org.example.bewerbungs_buddy.exceptions;

public class ApplicationCouldNotBeAddedException extends RuntimeException {
    public ApplicationCouldNotBeAddedException() {
        super("Application could not be added");
    }
}
