package org.example.bewerbungs_buddy.exceptions;

public class NotificationsCouldNotBeFoundByStatusException extends RuntimeException{
    public NotificationsCouldNotBeFoundByStatusException(String status){
        super("Could not find Notifications with status: " + status);
    }
}
