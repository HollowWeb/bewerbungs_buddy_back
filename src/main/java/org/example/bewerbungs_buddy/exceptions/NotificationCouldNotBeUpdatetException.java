package org.example.bewerbungs_buddy.exceptions;

public class NotificationCouldNotBeUpdatetException extends RuntimeException{
    public NotificationCouldNotBeUpdatetException(Long id){
        super("Notification with ID: " + id + " could not be updated");
    }
}
