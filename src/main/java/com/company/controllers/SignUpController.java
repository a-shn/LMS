package com.company.controllers;

import com.company.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String login, @RequestParam String email, @RequestParam String password) {
        boolean isAllGood = signUpService.signUp(login, email, password);
        if (isAllGood) {
            return "email_verifying";
        }
        return "redirect: /signup?error";
    }
}
