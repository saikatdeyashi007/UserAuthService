package dev.saikat.userauthservice.exceptions;

public class PasswordMissmatchException extends RuntimeException{
    public PasswordMissmatchException(String message) {
        super(message);
    }
}
