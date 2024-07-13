package org.example.bewerbungs_buddy.exceptions;

public class NotificationCouldNotBeDeletedException extends RuntimeException{
    public NotificationCouldNotBeDeletedException(Long id){
        super("Notification with application_id: "+ id +" could not be deleted");
    }
}
