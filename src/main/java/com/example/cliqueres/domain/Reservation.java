package com.example.cliqueres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "NAME")
  private String name;

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

  @Column(name = "RESERVED_BY")
  private String reservedBy;
}
