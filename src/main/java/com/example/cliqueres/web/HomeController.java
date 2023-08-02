package com.example.cliqueres.web;

import com.example.cliqueres.service.EventService;
import com.example.cliqueres.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final EventService eventService;
    private final ReservationService reservationService;
    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("bodyContent", "home");
//        model.addAttribute("upcomingEvents", eventService.findAll());
        return "home";
    }
}
