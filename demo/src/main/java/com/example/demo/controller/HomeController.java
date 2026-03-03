package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if (loginUserId == null) return "redirect:/login";
        return "redirect:/board/";
    }
}
