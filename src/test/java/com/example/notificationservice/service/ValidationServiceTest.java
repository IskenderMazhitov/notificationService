package com.example.notificationservice.service;

import static com.example.notificationservice.model.DestinationType.EMAIL;
import static com.example.notificationservice.model.DestinationType.SMS;

import com.example.notificationservice.dto.DestinationDto;
import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.exception.ValidationException;
import com.example.notificationservice.model.DestinationType;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidationServiceTest {

  @Autowired
  private ValidationService service;

  @Test
  public void testHappyPath() {
    var notification = NotificationDto
        .builder()
        .title("New Title")
        .message("New message")
        .destinations(List.of(
            DestinationDto.builder().type(EMAIL).target("asdf@gmail.com").build(),
            DestinationDto.builder().type(SMS).target("+8812123125").build()

        ))
        .build();

    Assertions.assertDoesNotThrow(() -> {service.validateNotification(notification);});
  }

  @Test
  public void testBadPath() {
    var notification = NotificationDto
        .builder()
        .title("       ")
        .message(null)
        .destinations(List.of(
            DestinationDto.builder().type(EMAIL).target("asdfasd").build(),
            DestinationDto.builder().type(SMS).target("kjdadkjs").build()

        ))
        .build();

    var exception = Assertions.assertThrows(ValidationException.class, () -> {
      service.validateNotification(notification);
    });

    var errors = exception.getErrorMessages();

    Assertions.assertEquals(4, errors.size());
    Assertions.assertEquals("Invalid title", errors.get(0));
    Assertions.assertEquals("Invalid message", errors.get(1));
    Assertions.assertEquals("Invalid value 'asdfasd' for destination type EMAIL", errors.get(2));
    Assertions.assertEquals("Invalid value 'kjdadkjs' for destination type SMS", errors.get(3));
  }

  @Test
  public void testBadPathWithExceededTitleLength() {
    var notification = NotificationDto
        .builder()
        .title("a23:26:11.883 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neitha23:26:11.883 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neitha23:26:11.883 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neitha23:26:11.883 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neitha23:26:11.883 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neither @ContextConfiguration nor @ContextHierarchy found for test class [ValidationServiceTest]: using SpringBootContextLoadersd")
        .message("disljf")
        .destinations(List.of(
            DestinationDto.builder().type(EMAIL).target("asd@gmail.com").build()
        ))
        .build();

    var exception = Assertions.assertThrows(ValidationException.class, () -> {
      service.validateNotification(notification);
    });

    var errors = exception.getErrorMessages();

    Assertions.assertEquals(1, errors.size());
    Assertions.assertEquals("Title length cannot exceed 256 chars", errors.get(0));
  }
  @Test
  public void testBadPathWithNullDestinationTargets() {
    var notification = NotificationDto
        .builder()
        .title("qew")
        .message("asd")
        .destinations(List.of(
            DestinationDto.builder().type(EMAIL).build(),
            DestinationDto.builder().type(SMS).target(null).build()
        ))
        .build();

    var exception = Assertions.assertThrows(ValidationException.class, () -> {
      service.validateNotification(notification);
    });

    var errors = exception.getErrorMessages();

    Assertions.assertEquals(2, errors.size());
    Assertions.assertEquals("Invalid value 'null' for destination type EMAIL", errors.get(0));
    Assertions.assertEquals("Invalid value 'null' for destination type SMS", errors.get(1));
  }

  @Test
  public void testBadPathWithUnsupportedDestinationType() {
    var notification = NotificationDto
        .builder()
        .title("qew")
        .message("asd")
        .destinations(List.of(
            DestinationDto.builder().type(DestinationType.PUSH).build()
        ))
        .build();

    var exception = Assertions.assertThrows(ValidationException.class, () -> {
      service.validateNotification(notification);
    });

    var errors = exception.getErrorMessages();
    Assertions.assertEquals(1, errors.size());
    Assertions.assertEquals("Unsupported destination type PUSH", errors.get(0));
  }

  @Test
  public void testBadPathWithNoDestinations() {
    var notification = NotificationDto
        .builder()
        .title("qew")
        .message("asd")
        .build();

    var exception = Assertions.assertThrows(ValidationException.class, () -> {
      service.validateNotification(notification);
    });

    var errors = exception.getErrorMessages();
    Assertions.assertEquals(1, errors.size());
    Assertions.assertEquals("At least one destinations is needed", errors.get(0));
  }



}