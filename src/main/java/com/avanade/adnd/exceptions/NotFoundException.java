package com.avanade.adnd.exceptions;

public class NotFoundException extends RuntimeException {
    private String entityNotFound;
    public NotFoundException(String entityNotFound){
        this.entityNotFound = entityNotFound;
    }
}
