package com.example.springsecurityrest.models;

import lombok.Data;

@Data
public class UserWebClient {
    private Long id;
    private String name;
    private String username;
    private String email;
}
