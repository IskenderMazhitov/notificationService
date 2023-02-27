package com.example.notificationservice.exception;

import java.util.List;

public class ValidationException extends RuntimeException {
  private final List<String> errorMessages;

  public ValidationException(List<String> errorMessages) {
    this.errorMessages = errorMessages;
  }

  public List<String> getErrorMessages() {
    return errorMessages;
  }
}
