package com.example.notificationservice.dto;


import com.example.notificationservice.model.NotificationConsumer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

  @Enumerated(value = EnumType.STRING)
  private NotificationConsumer consumer;

  private String title;

  private String message;

  private List<DestinationDto> destinations;
}
