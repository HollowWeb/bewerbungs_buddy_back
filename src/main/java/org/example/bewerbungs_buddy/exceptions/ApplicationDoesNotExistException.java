package org.example.bewerbungs_buddy.exceptions;

public class ApplicationDoesNotExistException extends RuntimeException{
    public ApplicationDoesNotExistException(Long id){
        super("Application with ID:"+ id +" doesn't exist");
    }
}
