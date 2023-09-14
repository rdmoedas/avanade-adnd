package com.avanade.adnd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InitiativeAlreadyDecidedException extends RuntimeException {
    public InitiativeAlreadyDecidedException(){
        super("Initiative already decided");
    }

}
