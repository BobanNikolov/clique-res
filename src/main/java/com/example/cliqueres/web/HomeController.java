package com.example.cliqueres.web;

import com.example.cliqueres.domain.Event;
import com.example.cliqueres.domain.User;
import com.example.cliqueres.service.EventService;
import com.example.cliqueres.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final EventService eventService;
    private final ReservationService reservationService;
    @GetMapping
    public String main(Model model) {
        model.addAttribute("event", new Event());
        return "home";
    }

    @PostMapping
    public String save(Event event, Model model) {
        model.addAttribute("event", event);
        return "saved";
    }
}
