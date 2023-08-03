package com.example.springsecurityrest.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestError {

  private String httpStatus;
  private String message;

  public RestError(String httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
