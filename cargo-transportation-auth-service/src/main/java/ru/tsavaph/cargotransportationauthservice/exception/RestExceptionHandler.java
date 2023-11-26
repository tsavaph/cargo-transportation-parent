package ru.tsavaph.cargotransportationauthservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> notFound(UserNotFoundException exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> notValid(MethodArgumentNotValidException exception) {
        var errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var rejectedValue = ((FieldError) error).getRejectedValue();
            var errorKey = Objects.isNull(rejectedValue) ? fieldName : fieldName + " " + rejectedValue;
            var errorMessage = error.getDefaultMessage();
            errors.put(errorKey, errorMessage);
        });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = UserInfoServiceException.class)
    public ResponseEntity<String> userInfo(UserInfoServiceException exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                exception.getHttpStatus()
        );
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String> alreadyExists(UserAlreadyExistsException exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> internal(Exception exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
