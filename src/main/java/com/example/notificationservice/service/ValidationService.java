package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.exception.ValidationException;
import java.util.List;

public interface ValidationService {
  void validateNotification(NotificationDto notification) throws ValidationException;

}
