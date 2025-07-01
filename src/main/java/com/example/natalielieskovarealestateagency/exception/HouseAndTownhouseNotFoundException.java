package com.example.natalielieskovarealestateagency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HouseAndTownhouseNotFoundException extends RuntimeException {
    public HouseAndTownhouseNotFoundException(String message) {
        super(message);
    }
}
