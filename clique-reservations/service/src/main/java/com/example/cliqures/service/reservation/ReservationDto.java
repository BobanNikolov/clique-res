package com.example.cliqures.service.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private String nameReservation;
    private Long numOfPeople;
    private Long numOfTables;
    private Long createdBy;
    private Long event;
}
