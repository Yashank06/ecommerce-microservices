package com.mycompany.ecommerce_microservices.usermanagementservice.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}