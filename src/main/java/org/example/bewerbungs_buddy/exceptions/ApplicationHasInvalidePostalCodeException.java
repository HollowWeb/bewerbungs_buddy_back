package org.example.bewerbungs_buddy.exceptions;

public class ApplicationHasInvalidePostalCodeException extends RuntimeException{
    public ApplicationHasInvalidePostalCodeException(String postalCode){
        super("Invalid postal code: " + postalCode);
    }
}
