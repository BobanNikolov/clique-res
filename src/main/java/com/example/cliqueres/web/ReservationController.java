package com.example.cliqueres.web;

import com.example.cliqueres.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReservationController {
  private final ReservationService reservationService;
}
