package com.example.springsecurityrest.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
}
