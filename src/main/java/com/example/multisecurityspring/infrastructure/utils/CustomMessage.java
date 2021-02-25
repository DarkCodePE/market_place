package com.example.multisecurityspring.infrastructure.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@AllArgsConstructor
@Component
public class CustomMessage {
    private final MessageSource messageSource;
    public String getLocalMessage(String key, String... params){
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }
}
