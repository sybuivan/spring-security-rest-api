package com.example.springsecurityrest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RefreshTokenDto {

  @NotBlank(message = "refreshToken not empty")
  private String refreshToken;
}
