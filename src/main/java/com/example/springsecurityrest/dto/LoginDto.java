package com.example.springsecurityrest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

  @NotBlank(message = "usernameOrEmail not empty")
  private String usernameOrEmail;

  @NotBlank(message = "password not empty")
  private String password;
}
