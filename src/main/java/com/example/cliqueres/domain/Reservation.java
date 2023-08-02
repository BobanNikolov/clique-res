package com.example.cliqueres.domain;

import com.example.cliqueres.domain.enums.Importance;
import com.example.cliqueres.domain.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RESERVATION")
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME_RESERVATION")
  private String name;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @Column(name = "NUMBER_OF_PEOPLE")
  private Integer numberOfPeople;

  @Column(name = "NUMBER_OF_TABLES")
  private Integer numberOfTables;

  @Column(name = "TEL_NR")
  private String telephoneNumber;

  @Column(name = "IMPORTANCE")
  @Enumerated(EnumType.STRING)
  private Importance importance;

  @Column(name = "TYPE_RES")
  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name = "DATE_FOR")
  private LocalDate reservedForDate;

  @ManyToOne
  @JoinColumn(name = "EVENT_ID")
  private Event event;
}
