package com.example.multisecurityspring.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class I18AbleException extends RuntimeException {

    protected Object[] params;

    public I18AbleException(String key, Object... args) {
        super(key);
        params = args;
    }
}