package org.example.bewerbungs_buddy.exceptions;

public class ApplicationCouldNotBeUpdatedException extends RuntimeException{
    public ApplicationCouldNotBeUpdatedException(Long id){
        super("Application could with ID: "+ id +"not be updated");
    }
}
