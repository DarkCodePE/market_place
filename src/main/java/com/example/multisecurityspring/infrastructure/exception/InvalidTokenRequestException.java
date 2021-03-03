package com.example.multisecurityspring.infrastructure.exception;

public class InvalidTokenRequestException extends I18AbleException{

    public InvalidTokenRequestException(String key, Object... args) {
        super(key, args);
    }
}
