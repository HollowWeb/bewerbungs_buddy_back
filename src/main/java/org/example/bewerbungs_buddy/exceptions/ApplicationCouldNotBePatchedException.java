package org.example.bewerbungs_buddy.exceptions;

public class ApplicationCouldNotBePatchedException extends RuntimeException{
    public ApplicationCouldNotBePatchedException(Long id){
        super("Application could with ID: "+ id +" not be patched");
    }
}
