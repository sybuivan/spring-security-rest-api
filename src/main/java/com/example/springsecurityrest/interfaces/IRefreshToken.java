package com.example.springsecurityrest.interfaces;

import com.example.springsecurityrest.models.RefreshToken;

import java.util.Optional;

public interface IRefreshToken {
    public Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByUserId(Long userId);
}
