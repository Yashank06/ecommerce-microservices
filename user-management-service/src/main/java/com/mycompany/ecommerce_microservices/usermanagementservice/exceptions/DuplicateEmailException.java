package com.mycompany.ecommerce_microservices.usermanagementservice.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
