package ru.tsavaph.cargotransportationauthservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public JwtServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
