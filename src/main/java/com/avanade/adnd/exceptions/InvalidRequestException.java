package com.avanade.adnd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    final String invalidRequest;

    public InvalidRequestException(String invalidRequest){
        this.invalidRequest = invalidRequest;
    }

    @Override
    public String getMessage() {
        return invalidRequest;
    }
}
