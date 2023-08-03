package com.example.springsecurityrest.exception;

import javax.naming.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

  private final String errorMessage;

  public CustomAuthenticationException(String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
