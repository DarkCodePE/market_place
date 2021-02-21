package com.example.multisecurityspring.domain.exception;

public class NoSuchElementFoundException extends RuntimeException{
    public NoSuchElementFoundException(String message){
        super(message);
    }
}
