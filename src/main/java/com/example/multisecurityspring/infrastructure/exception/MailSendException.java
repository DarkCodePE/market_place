package com.example.multisecurityspring.infrastructure.exception;

public class MailSendException extends I18AbleException{
    public MailSendException(String key, Object... args) {
        super(key, args);
    }
}
