package com.example.cliqueres.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "reservation", schema = "clique_res")
public class Reservation {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_reservation", nullable = false)
    private String nameReservation;

    @Column(name = "num_of_people")
    private Long numOfPeople;

    @Column(name = "num_of_tables", nullable = false)
    private Long numOfTables;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "event")
    private Long event;
}
