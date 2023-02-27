package com.example.notificationservice.service;


import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.model.Notification;
import java.util.List;


public interface NotificationService {

  public void createNotification
      (NotificationDto notificationDto);

  public List<Notification> getAllNotifications();

}
