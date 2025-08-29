package com.example.springsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Spring Security Demo!");
        return "home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("message", "This is the home page - accessible to everyone");
        return "home";
    }

    @GetMapping("/secured")
    public String secured(Model model, Authentication authentication) {
        model.addAttribute("message", "This is a secured page!");
        model.addAttribute("username", authentication.getName());
        model.addAttribute("authorities", authentication.getAuthorities());
        return "secured";
    }

    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication) {
        model.addAttribute("message", "This is the admin page!");
        model.addAttribute("username", authentication.getName());
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
