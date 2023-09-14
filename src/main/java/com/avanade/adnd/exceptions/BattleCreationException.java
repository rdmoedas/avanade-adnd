package com.avanade.adnd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BattleCreationException extends RuntimeException {
    private final String message;
    public BattleCreationException(String message){
        this.message = message;
    }
    @Override
    public String getMessage(){
        return this.message;
    }
}
