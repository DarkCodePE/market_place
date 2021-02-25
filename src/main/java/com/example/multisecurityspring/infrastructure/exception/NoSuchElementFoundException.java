package com.example.multisecurityspring.infrastructure.exception;

public class NoSuchElementFoundException extends RuntimeException{
    public NoSuchElementFoundException(String message){
        super(message);
    }
}
