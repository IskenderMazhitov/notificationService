package com.example.notificationservice.dto;

import com.example.notificationservice.model.DestinationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinationDto {
  @Enumerated(value = EnumType.STRING)
  private DestinationType type;
  private String target;

}
