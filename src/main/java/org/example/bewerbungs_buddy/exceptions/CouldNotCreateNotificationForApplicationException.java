package org.example.bewerbungs_buddy.exceptions;

public class CouldNotCreateNotificationForApplicationException extends RuntimeException{
    public CouldNotCreateNotificationForApplicationException(Long id){
        super("Couldn't create notification for Application with ID: " + id);
    }
}
