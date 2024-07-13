package org.example.bewerbungs_buddy.exceptions;

public class ApplicationHasInvalidePhoneNumberException extends RuntimeException{
    public ApplicationHasInvalidePhoneNumberException(String phoneNumber){
        super("Invalid phone number: " + phoneNumber);
    }
}
