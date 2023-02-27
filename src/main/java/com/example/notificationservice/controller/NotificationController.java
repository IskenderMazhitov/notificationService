package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.exception.ValidationException;
import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.service.ValidationService;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotificationController {

  private final NotificationService notificationService;
  private final ValidationService validationService;


  public NotificationController(NotificationService notificationService,
      ValidationService validationService) {
    this.notificationService = notificationService;
    this.validationService = validationService;
  }

  @PostMapping
  public ResponseEntity<?> sendNotify(@RequestBody NotificationDto notificationDto) {
    try {
      validationService.validateNotification(notificationDto);
      notificationService.createNotification(notificationDto);

      return ResponseEntity.ok().body("ok.");
    } catch (ValidationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "timestamp", OffsetDateTime.now(ZoneOffset.UTC),
          "status", HttpStatus.BAD_REQUEST,
          "errors", e.getErrorMessages()
      ));
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
          "timestamp", OffsetDateTime.now(ZoneOffset.UTC),
          "status", HttpStatus.INTERNAL_SERVER_ERROR,
          "error", e.getMessage()
      ));
    }

  }

  @GetMapping
  public ResponseEntity<?> getNotifications() {
    var notifications = notificationService.getAllNotifications();
    return ResponseEntity.ok().body(notifications);
  }

}
