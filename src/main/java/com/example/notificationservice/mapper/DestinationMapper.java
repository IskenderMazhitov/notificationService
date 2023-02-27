package com.example.notificationservice.mapper;

import com.example.notificationservice.dto.DestinationDto;
import com.example.notificationservice.model.Destination;

public class DestinationMapper {
  public static Destination mapDestionationDtoToDestination(DestinationDto dto) {
    return Destination.builder().type(dto.getType()).target(dto.getTarget().trim()).build();
  }

}
