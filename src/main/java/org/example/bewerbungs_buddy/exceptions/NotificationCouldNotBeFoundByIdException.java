package org.example.bewerbungs_buddy.exceptions;

public class NotificationCouldNotBeFoundByIdException extends RuntimeException{
    public NotificationCouldNotBeFoundByIdException(Long id){
        super("Notification with ID: " + id + " could not be found");
    }
}
