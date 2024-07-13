package org.example.bewerbungs_buddy.exceptions;

public class ApplicationCouldNotBeDeletedException extends RuntimeException{
    public ApplicationCouldNotBeDeletedException(Long id){
        super("Application with ID:"+ id +" could not be deleted");
    }
}
