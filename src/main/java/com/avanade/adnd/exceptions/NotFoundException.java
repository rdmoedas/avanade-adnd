package com.avanade.adnd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    final String entityNotFound;

    public NotFoundException(String entityNotFound){
        this.entityNotFound = entityNotFound;
    }
}
