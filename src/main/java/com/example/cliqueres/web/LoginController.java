package com.example.cliqueres.web;

import com.example.cliqueres.domain.exception.UserDoesNotExistException;
import com.example.cliqueres.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

  private final AuthService authService;

  @GetMapping
  public String getLoginPage(Model model) {
    model.addAttribute("bodyContent", "login");
    return "login";
  }

  @PostMapping
  public String login(HttpServletRequest request, Model model) {
    try {
      if (authService.checkUser(request.getParameter("username"),
          request.getParameter("password"))) {
        request.getSession().setAttribute("user", request.getParameter("password"));
      }
      return "redirect:/home";
    } catch (UserDoesNotExistException exception) {
      model.addAttribute("hasError", true);
      model.addAttribute("error", exception.getMessage());
      return "login";
    }
  }
}
