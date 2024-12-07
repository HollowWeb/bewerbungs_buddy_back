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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * JAVADOC 404
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationCouldNotBeAddedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleApplicationCouldNotBeAddedException(ApplicationCouldNotBeAddedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationCouldNotBeDeletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleApplicationCouldNotBeDeletedException(ApplicationCouldNotBeDeletedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationCouldNotBeFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleApplicationCouldNotBeFoundException(ApplicationCouldNotBeFoundException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationCouldNotBePatchedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleApplicationCouldNotBePatchedException(ApplicationCouldNotBePatchedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationCouldNotBeUpdatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleApplicationCouldNotBeUpdatedException(ApplicationCouldNotBeUpdatedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleApplicationDoesNotExistException(ApplicationDoesNotExistException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationHasInvalideContactInformationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleApplicationHasInvalideContactInformationException(ApplicationHasInvalideContactInformationException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationHasInvalidePhoneNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleApplicationHasInvalidePhoneNumberException(ApplicationHasInvalidePhoneNumberException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationHasInvalidePostalCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleApplicationHasInvalidePostalCodeException(ApplicationHasInvalidePostalCodeException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CouldNotCreateNotificationForApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleCouldNotCreateNotificationForApplicationException(CouldNotCreateNotificationForApplicationException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationCouldNotBeDeletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleNotificationCouldNotBeDeletedException(NotificationCouldNotBeDeletedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationCouldNotBeFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotificationCouldNotBeFoundByIdException(NotificationCouldNotBeFoundByIdException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationCouldNotBeSavedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleNotificationCouldNotBeSavedException(NotificationCouldNotBeSavedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationCouldNotBeUpdatetException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleNotificationCouldNotBeUpdatetException(NotificationCouldNotBeUpdatetException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationsCouldNotBeFoundByStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotificationsCouldNotBeFoundByStatusException(NotificationsCouldNotBeFoundByStatusException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationsCouldNotBeFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotificationsCouldNotBeFoundException(NotificationsCouldNotBeFoundException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationSendDateNotProvidedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleApplicationSendDateNotProvidedException(ApplicationSendDateNotProvidedException ex, WebRequest request) {
        return createDefaultErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> createDefaultErrorResponse(String exceptionMessage, HttpStatus status) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exceptionMessage);

        return new ResponseEntity<>(errors, status);
    }
}
