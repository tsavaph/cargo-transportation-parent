package ru.tsavaph.cargotransportationauthservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserInfoServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UserInfoServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
