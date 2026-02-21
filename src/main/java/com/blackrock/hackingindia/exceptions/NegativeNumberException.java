package com.blackrock.hackingindia.exceptions;

public class NegativeNumberException extends RuntimeException{
    public NegativeNumberException() {
        super("Negative numbers are not allowed.");
    }

    public NegativeNumberException(String message) {
        super(message);
    }

    public NegativeNumberException(String message, Throwable cause) {
        super(message, cause);
    }    
}
