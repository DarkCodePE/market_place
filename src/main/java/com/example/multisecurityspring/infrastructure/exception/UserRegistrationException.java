package com.example.multisecurityspring.infrastructure.exception;

public class UserRegistrationException extends I18AbleException{
    public UserRegistrationException(String key, Object... args) {
        super(key, args);
    }
}
