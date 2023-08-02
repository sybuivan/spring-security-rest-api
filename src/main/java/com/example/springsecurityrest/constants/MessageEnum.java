package com.example.springsecurityrest.constants;

public enum MessageEnum {
  SUCCESS("Operation successful."),
  CREATE("Create %s successfully"),
  GET("Get %s successfully"),
  DELETE("Delete %s successfully"),
  UPDATE("Update %s successfully"),
  ERROR("An error occurred."),
  NOT_FOUND("The %s with ID %d was not found."),
  ;

  private final String message;

  MessageEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getFormattedMessage(Object args1, Object args2) {
    return String.format(message, args1, args2);
  }

  public String getFormattedMessage(Object args1) {
    return String.format(message, args1);
  }
}
