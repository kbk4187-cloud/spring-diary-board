package com.example.kiroku.controller;

import com.example.kiroku.entity.UserEntity;
import com.example.kiroku.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//AutoController는 로그인과 회원가입 요청을 받아 Service에 로직 처리를 맡기고,
// 그 결과를 화면에 연결하는 역할
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        try {
            UserEntity user = userService.login(username, password);
            session.setAttribute("loginUserId", user.getId());
            return "redirect:/board/";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         RedirectAttributes ra) {
        try {
            userService.signup(username, password);
            ra.addFlashAttribute("successMessage", "회원가입 성공! 로그인 해주세요.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/signup";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
