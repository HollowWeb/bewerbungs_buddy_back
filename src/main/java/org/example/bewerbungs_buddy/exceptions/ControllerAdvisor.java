package org.example.bewerbungs_buddy.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Application-related exception handlers
    @ExceptionHandler(ApplicationCouldNotBeAddedException.class)
    public ResponseEntity<Object> handleApplicationCouldNotBeAddedException(ApplicationCouldNotBeAddedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationCouldNotBeDeletedException.class)
    public ResponseEntity<Object> handleApplicationCouldNotBeDeletedException(ApplicationCouldNotBeDeletedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationCouldNotBeFoundException.class)
    public ResponseEntity<Object> handleApplicationCouldNotBeFoundException(ApplicationCouldNotBeFoundException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationCouldNotBePatchedException.class)
    public ResponseEntity<Object> handleApplicationCouldNotBePatchedException(ApplicationCouldNotBePatchedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationCouldNotBeUpdatedException.class)
    public ResponseEntity<Object> handleApplicationCouldNotBeUpdatedException(ApplicationCouldNotBeUpdatedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationDoesNotExistException.class)
    public ResponseEntity<Object> handleApplicationDoesNotExistException(ApplicationDoesNotExistException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationHasInvalideContactInformationException.class)
    public ResponseEntity<Object> handleApplicationHasInvalideContactInformationException(ApplicationHasInvalideContactInformationException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationHasInvalidePhoneNumberException.class)
    public ResponseEntity<Object> handleApplicationHasInvalidePhoneNumberException(ApplicationHasInvalidePhoneNumberException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ApplicationHasInvalidePostalCodeException.class)
    public ResponseEntity<Object> handleApplicationHasInvalidePostalCodeException(ApplicationHasInvalidePostalCodeException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    // Notification-related exception handlers
    @ExceptionHandler(CouldNotCreateNotificationForApplicationException.class)
    public ResponseEntity<Object> handleCouldNotCreateNotificationForApplicationException(CouldNotCreateNotificationForApplicationException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotificationCouldNotBeDeletedException.class)
    public ResponseEntity<Object> handleNotificationCouldNotBeDeletedException(NotificationCouldNotBeDeletedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotificationCouldNotBeFoundByIdException.class)
    public ResponseEntity<Object> handleNotificationCouldNotBeFoundByIdException(NotificationCouldNotBeFoundByIdException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotificationCouldNotBeSavedException.class)
    public ResponseEntity<Object> handleNotificationCouldNotBeSavedException(NotificationCouldNotBeSavedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotificationCouldNotBeUpdatetException.class)
    public ResponseEntity<Object> handleNotificationCouldNotBeUpdatetException(NotificationCouldNotBeUpdatetException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotificationsCouldNotBeFoundByStatusException.class)
    public ResponseEntity<Object> handleNotificationsCouldNotBeFoundByStatusException(NotificationsCouldNotBeFoundByStatusException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotificationsCouldNotBeFoundException.class)
    public ResponseEntity<Object> handleNotificationsCouldNotBeFoundException(NotificationsCouldNotBeFoundException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage());
    }

    private ResponseEntity<Object> createDefaultErrorResponse(String exceptionMessage) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exceptionMessage);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
