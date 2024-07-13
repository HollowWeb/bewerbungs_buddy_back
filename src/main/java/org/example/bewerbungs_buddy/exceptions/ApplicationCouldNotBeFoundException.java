package org.example.bewerbungs_buddy.exceptions;

public class ApplicationCouldNotBeFoundException extends RuntimeException {
    public ApplicationCouldNotBeFoundException() {
        super("Applications could not be found");
    }
    public ApplicationCouldNotBeFoundException(String status) {
        super("Applications with status " + status + " could not be found");
    }

}
