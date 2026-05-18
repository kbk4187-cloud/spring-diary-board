package com.example.kiroku.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//HomeController는 사용자가 / 주소로 접속했을 때 기본 화면을 반환하는 역할
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if (loginUserId == null) return "redirect:/login";
        return "redirect:/board/";
    }
}
