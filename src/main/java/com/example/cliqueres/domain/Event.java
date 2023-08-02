package com.example.cliqueres.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVENT_NAME")
    private String name;

    @Column(name = "EVENT_AT")
    private LocalDate date;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<Reservation> reservations;
}
