package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.exception.ValidationException;
import com.example.notificationservice.model.DestinationType;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class DefaultValidationService implements ValidationService {

  private static final String REGEX_EMAIL = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
  private static final String REGEX_PHONE = "^\\+(?:[0-9] ?){6,14}[0-9]$";

  private static final Map<DestinationType, String> destinationTypeRegex = Map.of(
      DestinationType.EMAIL, REGEX_EMAIL, DestinationType.SMS, REGEX_PHONE);

  @Override
  public void validateNotification(NotificationDto notification) throws ValidationException {
    var validationErrors = new ArrayList<String>();

    var title = notification.getTitle();
    var message = notification.getMessage();
    var destinations = notification.getDestinations();

    if (Strings.isBlank(title)) {
      validationErrors.add("Invalid title");
    } else if (title.length() > 256) {
      validationErrors.add("Title length cannot exceed 256 chars");
    }

    if (Strings.isBlank(message)) {
      validationErrors.add("Invalid message");
    }

    if (Objects.isNull(destinations) || destinations.isEmpty()) {
      validationErrors.add("At least one destinations is needed");
    } else {
      destinations.stream().map(d -> {
        var type = d.getType();
        if (!destinationTypeRegex.containsKey(type)) {
          return String.format("Unsupported destination type %s", type);
        }

        var value = d.getTarget();
        var regex = destinationTypeRegex.get(type);
        var possibleError = String.format("Invalid value '%s' for destination type %s", value,
            type);

        return validateByRegex(value, regex, possibleError).orElse(null);
      }).filter(Strings::isNotBlank).collect(Collectors.toCollection(() -> validationErrors));
    }

    if (!validationErrors.isEmpty()) {
      throw new ValidationException(validationErrors);
    }
  }

  private Optional<String> validateByRegex(String value, String regex, String error) {
    if (Strings.isNotBlank(value) && value.matches(regex)) {
      return Optional.empty();
    }
    return Optional.of(error);
  }
}
