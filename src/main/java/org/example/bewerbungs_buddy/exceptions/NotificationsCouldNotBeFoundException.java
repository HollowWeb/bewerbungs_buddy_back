package org.example.bewerbungs_buddy.exceptions;

public class NotificationsCouldNotBeFoundException extends RuntimeException{
    public NotificationsCouldNotBeFoundException(){
        super("Notifications could not be found");
    }
}
