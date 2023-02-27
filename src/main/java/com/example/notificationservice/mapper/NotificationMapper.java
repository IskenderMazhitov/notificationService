package com.example.notificationservice.mapper;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.model.Notification;

public class NotificationMapper {

  public static Notification mapNotificationDtoToNotification(NotificationDto notificationDto) {
    var destinations = notificationDto.getDestinations().stream().map(
        DestinationMapper::mapDestionationDtoToDestination
    ).toList();
    return Notification.builder().consumer(notificationDto.getConsumer()).message(
            notificationDto.getMessage().trim()).destinations(destinations).title(notificationDto.getTitle().trim())
        .build();
  }

}
