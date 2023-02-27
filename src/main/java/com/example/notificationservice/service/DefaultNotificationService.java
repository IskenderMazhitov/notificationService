package com.example.notificationservice.service;

import static com.example.notificationservice.mapper.NotificationMapper.mapNotificationDtoToNotification;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultNotificationService implements NotificationService {


  private final NotificationRepository notificationRepository;
  public DefaultNotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }


  @Override
  public void createNotification(NotificationDto notificationDto) {
    // TODO:
    // [x] validation: check email, ...
    // [x] persist data in db
    // [] send message
    // [] update notification status to complete

    var notification = mapNotificationDtoToNotification(notificationDto);
    try {
      notificationRepository
          .saveAndFlush(notification);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());

    } finally {

      log.info("notification: {} {}", notification, 2 + 2);

      for (var d : notification.getDestinations()) {
        switch (d.getType()) {
          case SMS:
            // comm
            break;
//          case PUSH:
          case EMAIL:
            break;
          default:
            // lala
            break;
        }
      }
    }


  }

  @Override
  public List<Notification> getAllNotifications() {
    return notificationRepository.findAll();
  }


}
