package com.manish0890.skyline.service.exception;

/**
 * Custom {@link RuntimeException} wrapper used to handle not found related exceptions
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
