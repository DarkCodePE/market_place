package com.example.multisecurityspring.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserLoginException extends I18AbleException{
    public UserLoginException(String key, Object... args) {
        super(key, args);
    }
}
