package com.mycompany.ecommerce_microservices.usermanagementservice.models;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
