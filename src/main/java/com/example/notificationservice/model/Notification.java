package com.example.notificationservice.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @ColumnDefault("PENDING")
  private NotificationStatus status;

  @Enumerated(value = EnumType.STRING)
  @ColumnDefault("UNDEFINED")
  private NotificationConsumer consumer;

  private String title;

  private String message;

//  @Type(name="jsonb")
//  @Column(columnDefinition = "jsonb")
//  @Basic(fetch = FetchType.LAZY)
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Destination> destinations;

}
