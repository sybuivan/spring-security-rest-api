package com.example.springsecurityrest.repositories;

import com.example.springsecurityrest.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  RefreshToken findByToken(String token);

  int deleteByUserId(Long userId);

  boolean existsByUserId(Long userId);
}
