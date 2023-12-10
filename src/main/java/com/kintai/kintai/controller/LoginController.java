package com.kintai.kintai.controller;

import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        LoginMember loginMember = loginService.login(name);
        if (loginMember == null) {
            return "login/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute("Login", loginMember);
        return "redirect:" + redirectURL;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
