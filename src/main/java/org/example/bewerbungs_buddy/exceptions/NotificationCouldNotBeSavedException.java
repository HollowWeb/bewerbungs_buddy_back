package org.example.bewerbungs_buddy.exceptions;

public class NotificationCouldNotBeSavedException extends RuntimeException{
    public NotificationCouldNotBeSavedException(){
        super("Notification could not be saved");
    }
}
