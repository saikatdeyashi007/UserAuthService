package dev.saikat.userauthservice.exceptions;

public class UserAlreadySignedInException extends RuntimeException{

    public UserAlreadySignedInException(String message) {
        super(message);
    }
}
