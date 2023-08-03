package com.example.springsecurityrest.services;

import com.example.springsecurityrest.exception.TokenRefreshException;
import com.example.springsecurityrest.interfaces.IRefreshToken;
import com.example.springsecurityrest.models.RefreshToken;
import com.example.springsecurityrest.repositories.RefreshTokenRepository;
import com.example.springsecurityrest.repositories.UserRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenService implements IRefreshToken {

  @Value("${jwt.refreshExpirationMs}")
  private Long refreshTokenDurationMs;
  @Autowired
  private RefreshTokenRepository refreshTokenRepository;
  @Autowired
  private UserRepository userRepository;


  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return Optional.ofNullable(refreshTokenRepository.findByToken(token));
  }

  @Override
  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(),
          "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  @Transactional
  @Override
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUserId(userRepository.findById(userId).get().getId());
  }

  @Override
  public boolean existsByUserId(Long userId) {
    return refreshTokenRepository.existsByUserId(userId);
  }
}
