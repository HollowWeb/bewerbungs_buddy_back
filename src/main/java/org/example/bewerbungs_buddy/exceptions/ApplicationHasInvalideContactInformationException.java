package org.example.bewerbungs_buddy.exceptions;

public class ApplicationHasInvalideContactInformationException extends RuntimeException{
    public ApplicationHasInvalideContactInformationException(String contactInfo) {
        super("The contact information is invalid: " + contactInfo);
    }
}
