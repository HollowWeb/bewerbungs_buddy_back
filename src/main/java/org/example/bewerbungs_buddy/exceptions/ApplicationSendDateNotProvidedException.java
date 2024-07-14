package org.example.bewerbungs_buddy.exceptions;

public class ApplicationSendDateNotProvidedException extends RuntimeException{
    public ApplicationSendDateNotProvidedException(Long id) {
        super("Application Send Date Not Provided for Application with ID: " + id);
    }
}
