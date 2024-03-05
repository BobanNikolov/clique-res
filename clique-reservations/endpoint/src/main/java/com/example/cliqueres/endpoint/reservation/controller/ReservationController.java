package com.example.cliqueres.endpoint.reservation.controller;

import com.example.cliqueres.endpoint.reservation.dto.ReservationOut;
import com.example.cliqueres.service.reservation.ReservationService;
import com.example.cliqueres.service.reservation.dto.ReservationPersistCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
  private final ReservationService reservationService;
  private final ConversionService conversionService;

  @PostMapping("/save")
  public ResponseEntity<ReservationOut> save(@RequestBody ReservationPersistCommand reservation) {
    final var result = this.reservationService.save(reservation);
    final var convertedResult = this.conversionService.convert(result, ReservationOut.class);
    return ResponseEntity.ok(convertedResult);
  }

  @PutMapping("/update")
  public ResponseEntity<ReservationOut> update(@RequestBody ReservationPersistCommand reservation) {
    final var result = this.reservationService.update(reservation);
    final var convertedResult = this.conversionService.convert(result, ReservationOut.class);
    return ResponseEntity.ok(convertedResult);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Long id) {
    var deleted = this.reservationService.delete(id);
    return ResponseEntity.ok(deleted);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationOut> getById(@PathVariable(name = "id") Long id) {
    var result = this.reservationService.getById(id);
    final var convertedResult = this.conversionService.convert(result, ReservationOut.class);
    return ResponseEntity.ok(convertedResult);
  }

}
